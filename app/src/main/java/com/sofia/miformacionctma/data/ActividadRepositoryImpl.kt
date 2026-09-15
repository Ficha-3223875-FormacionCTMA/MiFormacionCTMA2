package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.data.local.ActividadDao
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.data.remote.RemoteActividadDataSource
import com.sofia.miformacionctma.data.remote.toEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ActividadRepositoryImpl(
    private val dao: ActividadDao,
    private val remote: RemoteActividadDataSource
) : ActividadRepository {

    override fun observarTodas(): Flow<List<ActividadFormativa>> =
        dao.observarTodas()
            .map { lista ->
                lista.map { it.toDomain() }
            }

    override fun buscarPorTexto(
        texto: String
    ): Flow<List<ActividadFormativa>> =
        dao.buscarPorTexto(texto)
            .map { lista ->
                lista.map { it.toDomain() }
            }

    override suspend fun buscarPorId(
        id: Long
    ): ActividadFormativa? {
        return dao.buscarPorId(id)?.toDomain()
    }

    override suspend fun agregar(
        actividad: ActividadFormativa
    ) {
        dao.insertar(actividad.toEntity())
    }

    override suspend fun actualizar(
        actividad: ActividadFormativa
    ) {
        dao.actualizar(actividad.toEntity())
    }

    override suspend fun eliminar(
        id: Long
    ) {
        dao.eliminarPorId(id)
    }

    override suspend fun refrescarDesdeServidor(): ActualizacionResultado {

        return try {

            // 1. Consultar el servicio REST mediante Retrofit.
            val actividadesDto =
                remote.obtenerActividades()

            // 2. Convertir los DTO recibidos en entidades de Room.
            val entidades =
                actividadesDto.map { dto ->
                    dto.toEntity()
                }

            dao.guardarTodasDesdeServidor(entidades)

            // 4. Informar que la actualización fue exitosa.
            ActualizacionResultado.Exitosa(
                cantidad = entidades.size,
                fecha = System.currentTimeMillis()
            )

        } catch (e: CancellationException) {

            // Las cancelaciones de corrutinas no deben ocultarse.
            throw e

        } catch (e: SocketTimeoutException) {

            ActualizacionResultado.Fallida(
                tipo = TipoErrorRemoto.TIMEOUT,
                mensaje = "La conexión con el servidor tardó demasiado."
            )

        } catch (e: HttpException) {

            when (e.code()) {

                401 -> {
                    ActualizacionResultado.Fallida(
                        tipo = TipoErrorRemoto.NO_AUTORIZADO,
                        mensaje = "No autorizado para consultar el servidor."
                    )
                }

                404 -> {
                    ActualizacionResultado.Fallida(
                        tipo = TipoErrorRemoto.NO_ENCONTRADO,
                        mensaje = "No se encontró el recurso solicitado."
                    )
                }

                in 500..599 -> {
                    ActualizacionResultado.Fallida(
                        tipo = TipoErrorRemoto.SERVIDOR,
                        mensaje = "El servidor presentó un error."
                    )
                }

                else -> {
                    ActualizacionResultado.Fallida(
                        tipo = TipoErrorRemoto.DESCONOCIDO,
                        mensaje = "Error HTTP ${e.code()}."
                    )
                }
            }

        } catch (e: SerializationException) {

            ActualizacionResultado.Fallida(
                tipo = TipoErrorRemoto.JSON_INVALIDO,
                mensaje = "La respuesta del servidor no tiene un JSON válido."
            )

        } catch (e: IOException) {

            ActualizacionResultado.Fallida(
                tipo = TipoErrorRemoto.SIN_CONEXION,
                mensaje = "No fue posible conectarse con el servidor."
            )

        } catch (e: Exception) {

            ActualizacionResultado.Fallida(
                tipo = TipoErrorRemoto.DESCONOCIDO,
                mensaje = e.message
                    ?: "No fue posible actualizar los datos."
            )
        }
    }
}