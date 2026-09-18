package com.sofia.miformacionctma

import com.sofia.miformacionctma.data.remote.ActividadApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ActividadApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ActividadApi

    @Before
    fun iniciarServidor() {
        server = MockWebServer()
        server.start()

        val json = Json {
            ignoreUnknownKeys = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()

        api = retrofit.create(ActividadApi::class.java)
    }

    @After
    fun cerrarServidor() {
        server.close()
    }

    @Test
    fun obtenerActividades_respuesta200() = runTest {

        server.enqueue(
            MockResponse(
                code = 200,
                body = """
                    [
                        {
                            "id": 1,
                            "titulo": "Actividad de prueba",
                            "descripcion": "Prueba del servidor",
                            "progreso": 50,
                            "diasRestantes": 5,
                            "prioridad": "MEDIA",
                            "fecha": "2026-09-20"
                        }
                    ]
                """.trimIndent()
            )
        )

        val resultado = api.obtenerActividades()

        assertEquals(1, resultado.size)
        assertEquals("Actividad de prueba", resultado[0].titulo)
        assertEquals(50, resultado[0].progreso)
    }

    @Test
    fun obtenerActividades_respuesta404() = runTest {

        server.enqueue(
            MockResponse(
                code = 404,
                body = """
                    {
                        "detail": "Actividades no encontradas"
                    }
                """.trimIndent()
            )
        )

        try {
            api.obtenerActividades()
            throw AssertionError("Se esperaba una excepción HTTP 404")
        } catch (error: HttpException) {
            assertEquals(404, error.code())
        }
    }

    @Test
    fun obtenerActividades_respuesta500() = runTest {

        server.enqueue(
            MockResponse(
                code = 500,
                body = """
                    {
                        "detail": "Error interno del servidor"
                    }
                """.trimIndent()
            )
        )

        try {
            api.obtenerActividades()
            throw AssertionError("Se esperaba una excepción HTTP 500")
        } catch (error: HttpException) {
            assertEquals(500, error.code())
        }
    }
}