package com.sofia.miformacionctma

import com.sofia.miformacionctma.data.ActividadRepository
import com.sofia.miformacionctma.data.preferences.PreferenciasSource
import com.sofia.miformacionctma.data.preferences.PreferenciasUi
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.ui.screens.ActividadesViewModel
import com.sofia.miformacionctma.ui.state.ListadoUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActividadesViewModelFlowTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeActividadRepository
    private lateinit var preferencias: FakePreferenciasSource
    private lateinit var viewModel: ActividadesViewModel

    private val actividad = ActividadFormativa(
        id = 1L,
        titulo = "Kotlin",
        descripcion = "Practicar Flow",
        progreso = 50,
        diasRestantes = 5,
        prioridad = Prioridad.MEDIA,
        fecha = "2099-12-31"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = FakeActividadRepository(listOf(actividad))
        preferencias = FakePreferenciasSource()
        viewModel = ActividadesViewModel(repository, preferencias)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun estadoInicialPasaAContenido() = runTest {
        val job = backgroundScope.launch {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is ListadoUiState.Contenido)
        assertEquals(1, (estado as ListadoUiState.Contenido).actividades.size)

        job.cancel()
    }

    @Test
    fun busquedaRapidaConservaLaMasReciente() = runTest {
        val job = backgroundScope.launch {
            viewModel.uiState.collect {}
        }

        viewModel.cambiarBusqueda("Ko")
        advanceTimeBy(100)
        viewModel.cambiarBusqueda("Kotlin")
        advanceTimeBy(300)
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is ListadoUiState.Contenido)
        assertEquals("Kotlin", (estado as ListadoUiState.Contenido).actividades.first().titulo)

        job.cancel()
    }

    @Test
    fun falloDeRepositorioProduceOperacionFallida() = runTest {
        repository.fallarOperaciones = true

        viewModel.agregar(
            com.sofia.miformacionctma.ui.state.FormularioActividadUiState(
                titulo = "Nueva",
                fecha = "2099-12-31",
                progreso = "10",
                puedeGuardar = true
            )
        )

        advanceUntilIdle()

        assertTrue(
            viewModel.operacionUiState.value
                is com.sofia.miformacionctma.ui.state.OperacionUiState.Fallida
        )
    }

    @Test
    fun listaVaciaProduceEstadoVacio() = runTest {
        repository.emitir(emptyList())

        val job = backgroundScope.launch {
            viewModel.uiState.collect {}
        }

        advanceUntilIdle()

        assertEquals(ListadoUiState.Vacio, viewModel.uiState.value)

        job.cancel()
    }
}

private class FakeActividadRepository(
    initial: List<ActividadFormativa>
) : ActividadRepository {

    var fallarOperaciones: Boolean = false

    private val datos = MutableStateFlow(initial)

    override fun observarTodas(): Flow<List<ActividadFormativa>> = datos

    override fun buscarPorTexto(texto: String): Flow<List<ActividadFormativa>> =
        datos.map { lista ->
            lista.filter { it.titulo.contains(texto, ignoreCase = true) }
        }

    override suspend fun buscarPorId(id: Long): ActividadFormativa? =
        datos.value.firstOrNull { it.id == id }

    override suspend fun agregar(actividad: ActividadFormativa) {
        if (fallarOperaciones) error("Fallo simulado")
        datos.value = datos.value + actividad
    }

    override suspend fun actualizar(actividad: ActividadFormativa) {
        datos.value = datos.value.map {
            if (it.id == actividad.id) actividad else it
        }
    }

    override suspend fun eliminar(id: Long) {
        datos.value = datos.value.filterNot { it.id == id }
    }

    fun emitir(lista: List<ActividadFormativa>) {
        datos.value = lista
    }
}

private class FakePreferenciasSource : PreferenciasSource {

    private val datos = MutableStateFlow(PreferenciasUi())

    override val preferencias: Flow<PreferenciasUi> = datos

    override suspend fun guardarCategoria(categoriaId: String?) {
        datos.value = datos.value.copy(categoriaId = categoriaId)
    }

    override suspend fun guardarOrden(orden: String) {
        datos.value = datos.value.copy(orden = orden)
    }

    override suspend fun guardarModoVisualizacion(modo: String) {
        datos.value = datos.value.copy(modoVisualizacion = modo)
    }

    override suspend fun guardarFiltrosActivos(activos: Boolean) = Unit
}
