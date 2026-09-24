package com.sofia.miformacionctma.data.repository

import android.content.ContentResolver
import android.net.Uri
import android.content.Intent
import com.sofia.miformacionctma.data.local.dao.EvidenciaDao
import com.sofia.miformacionctma.data.local.entity.EvidenciaEntity
import com.sofia.miformacionctma.data.remote.api.ActividadesApi
import com.sofia.miformacionctma.domain.evidence.EvidenciaSyncState
import com.sofia.miformacionctma.domain.evidence.EvidenciaValidator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class EvidenciaRepository(
    private val dao: EvidenciaDao,
    private val resolver: ContentResolver,
    private val api: ActividadesApi
) {
    fun observarTodas(): Flow<List<EvidenciaEntity>> = dao.observarTodas()

    suspend fun guardarLocal(actividadId: Long, uri: Uri): Result<Unit> = runCatching {
        runCatching { resolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) }
        val mime = resolver.getType(uri) ?: throw IllegalArgumentException("No se pudo identificar el tipo de archivo")
        val bytes = resolver.openInputStream(uri)?.use { input ->
            var total = 0L
            val buffer = ByteArray(8192)
            while (true) {
                val read = input.read(buffer)
                if (read < 0) break
                total += read
                require(total <= EvidenciaValidator.MAX_BYTES) { "La imagen supera el límite de 5 MB" }
            }
            total
        } ?: throw IllegalArgumentException("No fue posible leer la imagen")
        EvidenciaValidator.validar(mime, bytes)
        dao.guardar(EvidenciaEntity(actividadId = actividadId, uri = uri.toString(), mimeType = mime, tamanoBytes = bytes))
    }

    suspend fun eliminar(actividadId: Long) {
        val evidencia = dao.obtener(actividadId)
        dao.eliminar(actividadId)
        if (evidencia != null && evidencia.uri.contains(".fileprovider/")) {
            runCatching { resolver.delete(Uri.parse(evidencia.uri), null, null) }
        }
    }

    suspend fun subir(evidencia: EvidenciaEntity) {
        dao.cambiarEstado(evidencia.actividadId, EvidenciaSyncState.SUBIENDO.name)
        try {
            val bytes = resolver.openInputStream(Uri.parse(evidencia.uri))?.use { it.readBytes() }
                ?: throw IllegalArgumentException("No fue posible leer la evidencia")
            val body = bytes.toRequestBody(evidencia.mimeType.toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("evidencia", "evidencia_${evidencia.actividadId}.jpg", body)
            val response = api.subirEvidencia(evidencia.actividadId, part)
            if (!response.isSuccessful) error("El servidor rechazó la evidencia (${response.code()})")
            dao.cambiarEstado(evidencia.actividadId, EvidenciaSyncState.SINCRONIZADA.name)
        } catch (e: CancellationException) {
            dao.cambiarEstado(evidencia.actividadId, EvidenciaSyncState.LOCAL.name)
            throw e
        } catch (e: Exception) {
            dao.cambiarEstado(evidencia.actividadId, EvidenciaSyncState.FALLIDA.name)
            throw e
        }
    }
}
