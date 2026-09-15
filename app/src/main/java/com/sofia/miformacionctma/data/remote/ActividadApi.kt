package com.sofia.miformacionctma.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ActividadApi {

    @GET("v1/actividades")
    suspend fun obtenerActividades(): List<ActividadDto>

    @GET("v1/actividades/{id}")
    suspend fun obtenerActividad(
        @Path("id") id: Long
    ): ActividadDto
}