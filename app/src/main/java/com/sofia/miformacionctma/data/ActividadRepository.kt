package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.data.local.ActividadDao
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Resultado de una actualización desde el servicio web.
 *
 * Semana 8 - Servicios Web:
 * La aplicación mantiene Room como fuente de datos para la interfaz
 * y utiliza este resultado para informar si la actualización remota
 * fue exitosa o tuvo algún problema.
 */
sealed interface ActualizacionResultado {

    /**
     * La actualización desde el servidor terminó correctamente.
     */
    data class Exitosa(
        val cantidad: Int,
        val fecha: Long
    ) : ActualizacionResultado

    /**
     * La actualización falló.
     *
     * fechaCache permite conservar la información de la última
     * actualización válida disponible en Room.
     */
    data class Fallida(
        val tipo: TipoErrorRemoto,
        val mensaje: String,
        val fechaCache: Long? = null
    ) : ActualizacionResultado

    /**
     * Se utiliza cuando el repositorio solamente tiene implementación local.
     */
    data object NoDisponible : ActualizacionResultado
}

/**
 * Tipos de errores que pueden ocurrir al consumir el servicio REST.
 */
enum class TipoErrorRemoto {
    SIN_CONEXION,
    TIMEOUT,
    NO_AUTORIZADO,
    NO_ENCONTRADO,
    SERVIDOR,
    JSON_INVALIDO,
    DESCONOCIDO
}

/**
 * Contrato principal del repositorio de actividades.
 *
 * La interfaz mantiene las operaciones que ya utilizaban
 * las semanas anteriores y agrega la actualización remota
 * correspondiente a la Semana 8.
 */
interface ActividadRepository {

    fun observarTodas(): Flow<List<ActividadFormativa>>

    fun buscarPorTexto(texto: String): Flow<List<ActividadFormativa>>

    suspend fun buscarPorId(id: Long): ActividadFormativa?

    suspend fun agregar(actividad: ActividadFormativa)

    suspend fun actualizar(actividad: ActividadFormativa)

    suspend fun eliminar(id: Long)

    /**
     * Solicita una actualización de las actividades desde el servidor.
     *
     * La implementación local no necesita servidor, por eso devuelve
     * NoDisponible. La implementación de Semana 8 sobrescribe esta función.
     */
    suspend fun refrescarDesdeServidor(): ActualizacionResultado {
        return ActualizacionResultado.NoDisponible
    }
}

/**
 * Implementación del repositorio utilizando Room.
 *
 * Esta implementación se conserva para mantener funcionando
 * la persistencia local de las semanas anteriores.
 */
class RoomActividadRepository(
    private val dao: ActividadDao
) : ActividadRepository {

    override fun observarTodas(): Flow<List<ActividadFormativa>> =
        dao.observarTodas()
            .map { lista ->
                lista.map { it.toDomain() }
            }

    override fun buscarPorTexto(texto: String): Flow<List<ActividadFormativa>> =
        dao.buscarPorTexto(texto)
            .map { lista ->
                lista.map { it.toDomain() }
            }

    override suspend fun buscarPorId(id: Long): ActividadFormativa? =
        dao.buscarPorId(id)?.toDomain()

    override suspend fun agregar(actividad: ActividadFormativa) {
        dao.insertar(actividad.toEntity())
    }

    override suspend fun actualizar(actividad: ActividadFormativa) {
        dao.actualizar(actividad.toEntity())
    }

    override suspend fun eliminar(id: Long) {
        dao.eliminarPorId(id)
    }
}