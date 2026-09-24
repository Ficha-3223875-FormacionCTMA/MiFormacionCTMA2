package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.data.remote.api.ActividadesApi
import com.sofia.miformacionctma.domain.network.RemoteFailure
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
class RemoteActividadDataSourceTest {
    private lateinit var server: MockWebServer
    private lateinit var source: RemoteActividadDataSource

    @Before fun setUp() {
        server = MockWebServer().also { it.start() }
        val client = OkHttpClient.Builder().readTimeout(100, TimeUnit.MILLISECONDS).build()
        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build().create(ActividadesApi::class.java)
        source = RemoteActividadDataSource(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun respuesta200_devuelveActividades() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody("""[{"id":1,"titulo":"API","progreso":60,"dias_restantes":4,"prioridad":"ALTA"}]"""))
        val result = source.listar()
        assertEquals(1, result.size); assertEquals("API", result.first().titulo)
    }

    @Test fun respuestaVacia_esValida() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody("[]"))
        assertTrue(source.listar().isEmpty())
    }

    @Test fun respuesta401_seClasificaComoSesion() = runTest {
        server.enqueue(MockResponse().setResponseCode(401))
        val error = runCatching { source.listar() }.exceptionOrNull()
        assertTrue(error is RemoteFailure.Sesion)
    }

    @Test fun respuesta500_seClasificaComoServidor() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        val error = runCatching { source.listar() }.exceptionOrNull()
        assertTrue(error is RemoteFailure.Servidor)
    }

    @Test fun jsonInvalido_seClasificaComoDatosInvalidos() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{no-es-json}"))
        val error = runCatching { source.listar() }.exceptionOrNull()
        assertTrue(error is RemoteFailure.DatosInvalidos)
    }

    @Test fun timeout_seClasificaYNoDependeDeInternet() = runTest {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        val error = runCatching { source.listar() }.exceptionOrNull()
        assertTrue(error is RemoteFailure.Timeout)
    }
}
