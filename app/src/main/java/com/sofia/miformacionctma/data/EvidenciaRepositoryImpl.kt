package com.sofia.miformacionctma.data

import android.content.ContentResolver
import android.net.Uri
import com.sofia.miformacionctma.data.local.EvidenciaDao
import com.sofia.miformacionctma.data.local.EvidenciaEntity
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.data.remote.EnvioEvidenciaRemotoResultado
import com.sofia.miformacionctma.data.remote.EvidenciaRemoteDataSource
import com.sofia.miformacionctma.domain.ValidacionEvidencia
import com.sofia.miformacionctma.domain.validarEvidencia
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class EvidenciaRepositoryImpl(
    private val dao: EvidenciaDao,
    private val contentResolver: ContentResolver,
    private val remote: EvidenciaRemoteDataSource
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

        // Si se reemplaza una fotografía creada por nuestra app,
        // eliminamos el archivo anterior después de guardar
        // correctamente la nueva evidencia.
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

    override suspend fun sincronizar(
        actividadId: Long
    ): SincronizacionEvidenciaResultado {

        val entidadInicial =
            dao.buscarPorActividad(
                actividadId
            )
                ?: return SincronizacionEvidenciaResultado.Fallida(
                    "No hay una evidencia local para sincronizar."
                )

        // Antes de intentar la red dejamos persistido SUBIENDO.
        val entidadSubiendo =
            entidadInicial.copy(
                estado = EstadoEvidencia.SUBIENDO.name
            )

        dao.actualizar(
            entidadSubiendo
        )

        val evidenciaSubiendo =
            entidadSubiendo.toDomain()

        return try {

            when (
                val resultadoRemoto =
                    remote.enviar(
                        evidenciaSubiendo
                    )
            ) {

                EnvioEvidenciaRemotoResultado.Exitosa -> {

                    val actual =
                        dao.buscarPorActividad(
                            actividadId
                        )
                            ?: return SincronizacionEvidenciaResultado.Fallida(
                                "La evidencia ya no está disponible."
                            )

                    val sincronizada =
                        actual.copy(
                            estado =
                                EstadoEvidencia.SINCRONIZADA.name
                        )

                    dao.actualizar(
                        sincronizada
                    )

                    SincronizacionEvidenciaResultado.Exitosa(
                        sincronizada.toDomain()
                    )
                }

                is EnvioEvidenciaRemotoResultado.Fallida -> {

                    marcarComoFallida(
                        actividadId
                    )

                    SincronizacionEvidenciaResultado.Fallida(
                        resultadoRemoto.mensaje
                    )
                }
            }

        } catch (cancelacion: CancellationException) {

            // No ocultamos la cancelación de la corrutina.
            throw cancelacion

        } catch (_: Exception) {

            // Timeout, pérdida de red u otro fallo inesperado.
            // Solo cambia el estado: la URI y metadatos permanecen.
            marcarComoFallida(
                actividadId
            )

            SincronizacionEvidenciaResultado.Fallida(
                "No fue posible sincronizar la evidencia. Puedes reintentar."
            )
        }
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

        val evidencia =
            dao.buscarPorActividad(
                actividadId
            )

        dao.eliminarPorActividad(
            actividadId
        )

        // Una foto elegida desde la galería del usuario
        // nunca se elimina físicamente.
        if (
            evidencia != null &&
            evidencia.archivoPropio
        ) {

            eliminarArchivoPropio(
                evidencia
            )
        }
    }

    private suspend fun marcarComoFallida(
        actividadId: Long
    ) {

        val actual =
            dao.buscarPorActividad(
                actividadId
            )
                ?: return

        dao.actualizar(
            actual.copy(
                estado =
                    EstadoEvidencia.FALLIDA.name
            )
        )
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