package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    // Semana 9:
    // La URL depende del ambiente seleccionado:
    // dev, stage o prod.
    //
    // No se almacenan tokens ni credenciales aquí.
    private val baseUrl: String =
        BuildConfig.API_BASE_URL

    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    private val client =
        OkHttpClient.Builder()
            .connectTimeout(
                10,
                TimeUnit.SECONDS
            )
            .readTimeout(
                10,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                10,
                TimeUnit.SECONDS
            )
            .build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(
                baseUrl
            )
            .client(
                client
            )
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json"
                        .toMediaType()
                )
            )
            .build()

    val actividadApi: ActividadApi =
        retrofit.create(
            ActividadApi::class.java
        )
}