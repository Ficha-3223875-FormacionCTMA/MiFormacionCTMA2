package com.sofia.miformacionctma.data.remote

class RemoteActividadDataSource(
    private val api: ActividadApi
) {

    suspend fun obtenerActividades(): List<ActividadDto> {
        return api.obtenerActividades()
    }

    suspend fun obtenerActividad(id: Long): ActividadDto {
        return api.obtenerActividad(id)
    }
}