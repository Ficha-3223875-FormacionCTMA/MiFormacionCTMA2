package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.BuildConfig
import com.sofia.miformacionctma.data.remote.api.ActividadesApi
import com.sofia.miformacionctma.data.remote.session.TokenProvider
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
object NetworkProvider {
    fun api(tokenProvider: TokenProvider): ActividadesApi {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val token = tokenProvider.token()
                val request = chain.request().newBuilder().apply {
                    if (!token.isNullOrBlank()) header("Authorization", "Bearer $token")
                }.build()
                chain.proceed(request)
            }
            .build()
        val json = Json { ignoreUnknownKeys = true; explicitNulls = false }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ActividadesApi::class.java)
    }
}
