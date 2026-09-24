package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.data.Evidencia

/**
 * Implementación utilizada mientras el backend del proyecto
 * no exponga un endpoint definido para subir evidencias.
 *
 * No simula una sincronización exitosa ni inventa rutas.
 */
class EvidenciaRemoteDataSourceNoConfigurado :
    EvidenciaRemoteDataSource {

    override suspend fun enviar(
        evidencia: Evidencia
    ): EnvioEvidenciaRemotoResultado {

        return EnvioEvidenciaRemotoResultado.Fallida(
            "El servicio de sincronización de evidencias no está configurado. Puedes reintentar más tarde."
        )
    }
}