package com.sofia.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofia.miformacionctma.data.preferences.PreferenciasDataSource
import com.sofia.miformacionctma.data.preferences.PreferenciasUi
import com.sofia.miformacionctma.data.repository.ActividadDataSource
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ActividadViewModel(
    private val repository: ActividadDataSource,
    private val preferencesRepository: PreferenciasDataSource
) : ViewModel() {

    private val textoBusqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = textoBusqueda.asStateFlow()

    private val _operacion =
        MutableStateFlow<OperacionUiState>(OperacionUiState.Inactiva)
    val operacion: StateFlow<OperacionUiState> = _operacion.asStateFlow()

    private val forzarError = MutableStateFlow(false)

    private val _refresh =
        MutableStateFlow<RefreshUiState>(RefreshUiState.Inactiva)
    val refresh: StateFlow<RefreshUiState> = _refresh.asStateFlow()

    val preferencias: StateFlow<PreferenciasUi> =
        preferencesRepository.preferencias.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PreferenciasUi()
        )

    private sealed interface CargaResultado {
        data class Exito(
            val lista: List<ActividadFormativa>
        ) : CargaResultado

        data class Fallo(
            val mensaje: String
        ) : CargaResultado
    }

    private val actividadesBuscadas: Flow<CargaResultado> =
        combine(
            textoBusqueda,
            forzarError
        ) { texto, error ->
            texto.trim() to error
        }
            .distinctUntilChanged()
            .flatMapLatest { (texto, error) ->

                val fuente = when {
                    error ->
                        repository.observarConFalloSimulado()

                    texto.isBlank() ->
                        repository.observarTodas()

                    else ->
                        repository.buscarPorTitulo(texto)
                }

                fuente
                    .map<List<ActividadFormativa>, CargaResultado> {
                        CargaResultado.Exito(it)
                    }
                    .catch { fallo ->
                        if (fallo is CancellationException) {
                            throw fallo
                        }

                        emit(
                            CargaResultado.Fallo(
                                "No fue posible cargar las actividades. Puedes reintentar."
                            )
                        )
                    }
            }

    val uiState: StateFlow<ListadoUiState> =
        combine(
            actividadesBuscadas,
            preferencesRepository.preferencias
        ) { resultado, pref ->

            when (resultado) {

                is CargaResultado.Fallo ->
                    ListadoUiState.Error(
                        resultado.mensaje
                    )

                is CargaResultado.Exito -> {

                    val filtradas =
                        if (pref.filtroPrioridad == "TODAS") {
                            resultado.lista
                        } else {
                            resultado.lista.filter {
                                it.prioridad.name ==
                                        pref.filtroPrioridad
                            }
                        }

                    val ordenadas =
                        when (pref.orden) {

                            "TITULO" ->
                                filtradas.sortedBy {
                                    it.titulo.lowercase()
                                }

                            "PROGRESO" ->
                                filtradas.sortedByDescending {
                                    it.progreso
                                }

                            else ->
                                filtradas.sortedBy {
                                    it.diasRestantes
                                }
                        }

                    if (ordenadas.isEmpty()) {
                        ListadoUiState.Vacio
                    } else {
                        ListadoUiState.Contenido(
                            ordenadas
                        )
                    }
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        5_000
                    ),
                initialValue =
                    ListadoUiState.Cargando
            )

    fun cambiarBusqueda(
        texto: String
    ) {
        textoBusqueda.value = texto
    }

    fun guardarOrden(
        orden: String
    ) = ejecutarPreferencia {
        preferencesRepository.guardarOrden(
            orden
        )
    }

    fun guardarFiltroPrioridad(
        filtro: String
    ) = ejecutarPreferencia {
        preferencesRepository.guardarFiltroPrioridad(
            filtro
        )
    }

    fun guardarModoVisualizacion(
        modo: String
    ) = ejecutarPreferencia {
        preferencesRepository.guardarModoVisualizacion(
            modo
        )
    }

    fun insertar(
        actividad: ActividadFormativa
    ) = ejecutarOperacion(
        "Actividad guardada"
    ) {
        repository.insertar(
            actividad
        )
    }

    fun actualizar(
        actividad: ActividadFormativa
    ) = ejecutarOperacion(
        "Actividad actualizada"
    ) {
        repository.actualizar(
            actividad
        )
    }

    fun eliminar(
        actividad: ActividadFormativa
    ) = ejecutarOperacion(
        "Actividad eliminada"
    ) {
        repository.eliminar(
            actividad
        )
    }

    fun actualizarDesdeRed() {
        viewModelScope.launch {

            _refresh.value =
                RefreshUiState.EnCurso

            try {

                val cantidad =
                    repository.refresh()

                _refresh.value =
                    RefreshUiState.Exitosa(
                        "Actualización completa: $cantidad actividades",
                        System.currentTimeMillis()
                    )

            } catch (
                cancelada: CancellationException
            ) {

                throw cancelada

            } catch (
                error: Exception
            ) {

                _refresh.value =
                    RefreshUiState.Fallida(
                        error.message
                            ?: "No fue posible actualizar."
                    )
            }
        }
    }

    fun reintentar() {
        forzarError.value = false
    }

    fun simularError() {
        forzarError.value = true
    }

    fun limpiarOperacion() {
        _operacion.value =
            OperacionUiState.Inactiva
    }

    private fun ejecutarPreferencia(
        bloque: suspend () -> Unit
    ) {
        viewModelScope.launch {

            try {

                bloque()

            } catch (
                cancelada: CancellationException
            ) {

                throw cancelada

            } catch (
                _: Exception
            ) {

                _operacion.value =
                    OperacionUiState.Fallida(
                        "No fue posible guardar la preferencia."
                    )
            }
        }
    }

    private fun ejecutarOperacion(
        mensaje: String,
        bloque: suspend () -> Unit
    ) {
        viewModelScope.launch {

            _operacion.value =
                OperacionUiState.EnCurso

            try {

                bloque()

                _operacion.value =
                    OperacionUiState.Exitosa(
                        mensaje
                    )

            } catch (
                cancelada: CancellationException
            ) {

                throw cancelada

            } catch (
                _: Exception
            ) {

                _operacion.value =
                    OperacionUiState.Fallida(
                        "La operación no pudo completarse. Intenta nuevamente."
                    )
            }
        }
    }
}