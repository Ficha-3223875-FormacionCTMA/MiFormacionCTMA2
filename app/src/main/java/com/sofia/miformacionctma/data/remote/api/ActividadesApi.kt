package com.sofia.miformacionctma.data.remote.api

import com.sofia.miformacionctma.data.remote.dto.ActividadDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ActividadesApi {
    @GET("v1/actividades") suspend fun listar(): Response<List<ActividadDto>>
    @GET("v1/actividades/{id}") suspend fun obtener(@Path("id") id: Long): Response<ActividadDto>

    @Multipart
    @POST("v1/actividades/{id}/evidencias")
    suspend fun subirEvidencia(
        @Path("id") id: Long,
        @Part archivo: MultipartBody.Part
    ): Response<Unit>
}
