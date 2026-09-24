package com.sofia.miformacionctma.data

import android.content.ContentResolver
import android.net.Uri
import com.sofia.miformacionctma.data.local.EvidenciaDao
import com.sofia.miformacionctma.data.local.EvidenciaEntity
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.domain.ValidacionEvidencia
import com.sofia.miformacionctma.domain.validarEvidencia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class EvidenciaRepositoryImpl(
    private val dao: EvidenciaDao,
    private val contentResolver: ContentResolver
) : EvidenciaRepository {

    override fun observarPorActividad(
        actividadId: Long
    ): Flow<Evidencia?> =
        dao.observarPorActividad(actividadId)
            .map { entidad ->
                entidad?.toDomain()
            }

    override suspend fun buscarPorActividad(
        actividadId: Long
    ): Evidencia? =
        dao.buscarPorActividad(actividadId)
            ?.toDomain()

    override suspend fun registrarLocal(
        actividadId: Long,
        uri: String,
        mimeType: String,
        tamanoBytes: Long,
        nombreArchivo: String,
        archivoPropio: Boolean
    ): RegistroEvidenciaResultado {

        val validacion =
            validarEvidencia(
                actividadId = actividadId,
                uri = uri,
                mimeType = mimeType,
                tamanoBytes = tamanoBytes,
                nombreArchivo = nombreArchivo
            )

        if (validacion is ValidacionEvidencia.Invalida) {
            return RegistroEvidenciaResultado.Invalida(
                validacion.mensaje
            )
        }

        val uriAndroid =
            Uri.parse(uri)

        if (!uriEsLegible(uriAndroid)) {
            return RegistroEvidenciaResultado.Invalida(
                "La aplicación no puede leer la evidencia seleccionada."
            )
        }

        // Evidencia anterior asociada a la actividad.
        val existente =
            dao.buscarPorActividad(
                actividadId
            )

        val evidencia =
            Evidencia(
                id = existente?.id ?: 0,
                actividadId = actividadId,
                uri = uri,
                mimeType = mimeType,
                tamanoBytes = tamanoBytes,
                nombreArchivo = nombreArchivo,
                estado = EstadoEvidencia.LOCAL,
                archivoPropio = archivoPropio
            )

        val idGenerado =
            dao.insertar(
                evidencia.toEntity()
            )

        val evidenciaGuardada =
            if (evidencia.id == 0L) {

                evidencia.copy(
                    id = idGenerado
                )

            } else {

                evidencia
            }

        // Si reemplazamos una fotografía creada por nuestra propia
        // aplicación, eliminamos el archivo anterior después de que
        // la nueva evidencia quedó registrada correctamente.
        if (
            existente != null &&
            existente.archivoPropio &&
            existente.uri != uri
        ) {

            eliminarArchivoPropio(
                existente
            )
        }

        return RegistroEvidenciaResultado.Exitosa(
            evidenciaGuardada
        )
    }

    override suspend fun actualizarEstado(
        actividadId: Long,
        estado: EstadoEvidencia
    ) {

        val actual =
            dao.buscarPorActividad(
                actividadId
            )
                ?: return

        dao.actualizar(
            actual.copy(
                estado = estado.name
            )
        )
    }

    override suspend fun eliminar(
        actividadId: Long
    ) {

        // Primero recuperamos la evidencia porque necesitamos
        // saber si el archivo pertenece a nuestra aplicación.
        val evidencia =
            dao.buscarPorActividad(
                actividadId
            )

        // Eliminamos el registro persistido en Room.
        dao.eliminarPorActividad(
            actividadId
        )

        // Solo eliminamos físicamente archivos creados por
        // nuestra aplicación. Una foto elegida desde la galería
        // del usuario nunca debe borrarse.
        if (
            evidencia != null &&
            evidencia.archivoPropio
        ) {

            eliminarArchivoPropio(
                evidencia
            )
        }
    }

    private fun uriEsLegible(
        uri: Uri
    ): Boolean {

        return try {

            contentResolver
                .openInputStream(uri)
                ?.use {
                    true
                }
                ?: false

        } catch (_: SecurityException) {

            false

        } catch (_: IOException) {

            false
        }
    }

    private fun eliminarArchivoPropio(
        evidencia: EvidenciaEntity
    ) {

        if (!evidencia.archivoPropio) {
            return
        }

        runCatching {

            contentResolver.delete(
                Uri.parse(
                    evidencia.uri
                ),
                null,
                null
            )
        }
    }
}