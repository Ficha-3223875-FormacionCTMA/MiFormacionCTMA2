package com.sofia.miformacionctma.data

import kotlinx.coroutines.flow.Flow

/**
 * Estados posibles de una evidencia.
 *
 * Semana 9 - Capacidades del dispositivo y seguridad.
 */
enum class EstadoEvidencia {
    LOCAL,
    SUBIENDO,
    SINCRONIZADA,
    FALLIDA
}

/**
 * Modelo utilizado fuera de Room.
 *
 * La aplicación conserva solamente la URI de la imagen.
 * No almacena Bitmap, Base64 ni rutas físicas del dispositivo.
 */
data class Evidencia(
    val id: Long = 0,
    val actividadId: Long,
    val uri: String,
    val mimeType: String,
    val tamanoBytes: Long,
    val nombreArchivo: String,
    val estado: EstadoEvidencia,
    val archivoPropio: Boolean
)

/**
 * Resultado al intentar registrar una evidencia local.
 */
sealed interface RegistroEvidenciaResultado {

    data class Exitosa(
        val evidencia: Evidencia
    ) : RegistroEvidenciaResultado

    data class Invalida(
        val mensaje: String
    ) : RegistroEvidenciaResultado
}

/**
 * Resultado del intento de sincronización.
 *
 * Un fallo no elimina la evidencia local.
 */
sealed interface SincronizacionEvidenciaResultado {

    data class Exitosa(
        val evidencia: Evidencia
    ) : SincronizacionEvidenciaResultado

    data class Fallida(
        val mensaje: String
    ) : SincronizacionEvidenciaResultado
}

/**
 * Contrato del repositorio de evidencias.
 *
 * La interfaz de usuario no manipula directamente Room,
 * Retrofit ni rutas físicas de archivos.
 */
interface EvidenciaRepository {

    /**
     * Observa la evidencia asociada a una actividad.
     */
    fun observarPorActividad(
        actividadId: Long
    ): Flow<Evidencia?>

    /**
     * Consulta una evidencia una sola vez.
     */
    suspend fun buscarPorActividad(
        actividadId: Long
    ): Evidencia?

    /**
     * Registra una evidencia seleccionada
     * o capturada en el dispositivo.
     */
    suspend fun registrarLocal(
        actividadId: Long,
        uri: String,
        mimeType: String,
        tamanoBytes: Long,
        nombreArchivo: String,
        archivoPropio: Boolean
    ): RegistroEvidenciaResultado

    /**
     * Intenta enviar la evidencia asociada a la actividad.
     *
     * Flujo esperado:
     * LOCAL/FALLIDA -> SUBIENDO -> SINCRONIZADA
     *
     * Si ocurre un error:
     * SUBIENDO -> FALLIDA
     *
     * La URI y los metadatos locales se conservan.
     */
    suspend fun sincronizar(
        actividadId: Long
    ): SincronizacionEvidenciaResultado

    /**
     * Cambia el estado local de sincronización.
     */
    suspend fun actualizarEstado(
        actividadId: Long,
        estado: EstadoEvidencia
    )

    /**
     * Elimina la evidencia registrada para una actividad.
     */
    suspend fun eliminar(
        actividadId: Long
    )
}