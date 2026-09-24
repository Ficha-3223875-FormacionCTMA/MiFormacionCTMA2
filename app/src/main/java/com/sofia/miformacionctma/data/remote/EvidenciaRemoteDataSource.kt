package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.data.Evidencia

/**
 * Resultado del envío remoto de una evidencia.
 *
 * La capa remota no modifica Room directamente.
 */
sealed interface EnvioEvidenciaRemotoResultado {

    data object Exitosa :
        EnvioEvidenciaRemotoResultado

    data class Fallida(
        val mensaje: String
    ) : EnvioEvidenciaRemotoResultado
}

/**
 * Contrato para enviar evidencias al servidor.
 *
 * La implementación concreta podrá usar Retrofit,
 * pero Repository y UI no conocen Retrofit directamente.
 */
interface EvidenciaRemoteDataSource {

    suspend fun enviar(
        evidencia: Evidencia
    ): EnvioEvidenciaRemotoResultado
}