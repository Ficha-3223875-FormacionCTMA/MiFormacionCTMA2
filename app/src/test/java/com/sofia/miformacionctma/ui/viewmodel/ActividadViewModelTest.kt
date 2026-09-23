package com.sofia.miformacionctma.ui.viewmodel

import com.sofia.miformacionctma.data.preferences.PreferenciasDataSource
import com.sofia.miformacionctma.data.preferences.PreferenciasUi
import com.sofia.miformacionctma.data.repository.ActividadDataSource
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActividadViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun sinActividades_transitaDeCargandoAVacio() = runTest {
        val vm = ActividadViewModel(
            FakeActividadRepository(),
            FakePreferencias()
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            vm.uiState.collect {}
        }

        advanceUntilIdle()

        assertEquals(
            ListadoUiState.Vacio,
            vm.uiState.value
        )

        job.cancel()
    }

    @Test
    fun insertar_emiteContenidoYOperacionExitosa() = runTest {
        val repo = FakeActividadRepository()

        val vm = ActividadViewModel(
            repo,
            FakePreferencias()
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            vm.uiState.collect {}
        }

        runCurrent()

        vm.insertar(
            actividad("Room reactivo")
        )

        advanceUntilIdle()

        assertTrue(
            vm.uiState.value is ListadoUiState.Contenido
        )

        assertTrue(
            vm.operacion.value is OperacionUiState.Exitosa
        )

        job.cancel()
    }

    @Test
    fun nuevaBusqueda_cancelaLaAnterior() = runTest {
        val repo = FakeActividadRepository(
            conRetraso = true
        )

        val vm = ActividadViewModel(
            repo,
            FakePreferencias()
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            vm.uiState.collect {}
        }

        runCurrent()

        vm.cambiarBusqueda("com")
        runCurrent()

        vm.cambiarBusqueda("compose")

        advanceUntilIdle()

        assertTrue(
            "com" in repo.consultasCanceladas
        )

        assertEquals(
            listOf("compose"),
            repo.consultasCompletadas
        )

        job.cancel()
    }

    @Test
    fun filtroPersistente_recalculaContenido() = runTest {
        val pref = FakePreferencias()

        val repo = FakeActividadRepository(
            listOf(
                actividad(
                    "Alta",
                    Prioridad.ALTA
                ),
                actividad(
                    "Baja",
                    Prioridad.BAJA
                )
            )
        )

        val vm = ActividadViewModel(
            repo,
            pref
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            vm.uiState.collect {}
        }

        runCurrent()

        pref.guardarFiltroPrioridad("ALTA")

        advanceUntilIdle()

        val contenido =
            vm.uiState.value as ListadoUiState.Contenido

        assertEquals(
            listOf("Alta"),
            contenido.actividades.map {
                it.titulo
            }
        )

        job.cancel()
    }

    @Test
    fun errorSimulado_muestraError_yReintentarRecupera() = runTest {
        val vm = ActividadViewModel(
            FakeActividadRepository(
                listOf(
                    actividad("Recuperada")
                )
            ),
            FakePreferencias()
        )

        val job = backgroundScope.launch(
            UnconfinedTestDispatcher(testScheduler)
        ) {
            vm.uiState.collect {}
        }

        runCurrent()

        vm.simularError()

        advanceUntilIdle()

        assertTrue(
            vm.uiState.value is ListadoUiState.Error
        )

        vm.reintentar()

        advanceUntilIdle()

        assertTrue(
            vm.uiState.value is ListadoUiState.Contenido
        )

        job.cancel()
    }

    private fun actividad(
        titulo: String,
        prioridad: Prioridad = Prioridad.MEDIA
    ) = ActividadFormativa(
        id = titulo.hashCode().toLong(),
        titulo = titulo,
        descripcion = null,
        progreso = 50,
        diasRestantes = 3,
        prioridad = prioridad
    )
}

private class FakePreferencias : PreferenciasDataSource {

    private val estado =
        MutableStateFlow(PreferenciasUi())

    override val preferencias: Flow<PreferenciasUi> =
        estado

    override suspend fun guardarOrden(
        orden: String
    ) {
        estado.value =
            estado.value.copy(
                orden = orden
            )
    }

    override suspend fun guardarFiltroPrioridad(
        filtro: String
    ) {
        estado.value =
            estado.value.copy(
                filtroPrioridad = filtro
            )
    }

    override suspend fun guardarModoVisualizacion(
        modo: String
    ) {
        estado.value =
            estado.value.copy(
                modoVisualizacion = modo
            )
    }
}

private class FakeActividadRepository(
    iniciales: List<ActividadFormativa> = emptyList(),
    private val conRetraso: Boolean = false
) : ActividadDataSource {

    private val datos =
        MutableStateFlow(iniciales)

    val consultasCompletadas =
        mutableListOf<String>()

    val consultasCanceladas =
        mutableListOf<String>()

    override fun observarTodas():
            Flow<List<ActividadFormativa>> =
        datos

    override fun observarConFalloSimulado():
            Flow<List<ActividadFormativa>> =
        flow {
            throw IllegalStateException(
                "Fallo simulado"
            )
        }

    override fun observarPorId(
        id: Long
    ): Flow<ActividadFormativa?> =
        flow {
            emit(
                datos.value.find {
                    it.id == id
                }
            )
        }

    override fun buscarPorTitulo(
        texto: String
    ): Flow<List<ActividadFormativa>> =
        flow {
            var completada = false

            try {
                if (conRetraso) {
                    delay(
                        if (texto == "com") {
                            1_000
                        } else {
                            100
                        }
                    )
                }

                consultasCompletadas += texto
                completada = true

                emit(
                    datos.value.filter {
                        it.titulo.contains(
                            texto,
                            ignoreCase = true
                        )
                    }
                )

            } catch (
                cancelada: CancellationException
            ) {
                throw cancelada

            } finally {
                if (
                    conRetraso &&
                    !completada
                ) {
                    consultasCanceladas += texto
                }
            }
        }

    override suspend fun insertar(
        actividad: ActividadFormativa
    ): Long {

        val id =
            if (actividad.id == 0L) {
                (datos.value.maxOfOrNull {
                    it.id
                } ?: 0L) + 1
            } else {
                actividad.id
            }

        datos.value =
            datos.value +
                    actividad.copy(
                        id = id
                    )

        return id
    }

    override suspend fun actualizar(
        actividad: ActividadFormativa
    ) {
        datos.value =
            datos.value.map {
                if (it.id == actividad.id) {
                    actividad
                } else {
                    it
                }
            }
    }

    override suspend fun eliminar(
        actividad: ActividadFormativa
    ) {
        datos.value =
            datos.value.filterNot {
                it.id == actividad.id
            }
    }

    override suspend fun eliminarTodas() {
        datos.value = emptyList()
    }
}