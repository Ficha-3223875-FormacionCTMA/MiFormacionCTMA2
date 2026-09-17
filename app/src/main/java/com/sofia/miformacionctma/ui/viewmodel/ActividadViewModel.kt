package com.sofia.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofia.miformacionctma.data.preferences.PreferencesRepository
import com.sofia.miformacionctma.data.repository.ActividadRepository
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActividadViewModel(
    private val repository: ActividadRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val ordenActividades: StateFlow<String> =
        preferencesRepository.ordenActividades
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "FECHA"
            )

    val actividades: StateFlow<List<ActividadFormativa>> =
        combine(
            repository.observarTodas(),
            preferencesRepository.ordenActividades
        ) { lista, orden ->

            when (orden) {

                "TITULO" ->
                    lista.sortedBy {
                        it.titulo.lowercase()
                    }

                "PROGRESO" ->
                    lista.sortedByDescending {
                        it.progreso
                    }

                else ->
                    lista.sortedBy {
                        it.diasRestantes
                    }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun guardarOrden(orden: String) {
        viewModelScope.launch {
            preferencesRepository.guardarOrden(orden)
        }
    }

    fun insertar(actividad: ActividadFormativa) {
        viewModelScope.launch {
            repository.insertar(actividad)
        }
    }

    fun actualizar(actividad: ActividadFormativa) {
        viewModelScope.launch {
            repository.actualizar(actividad)
        }
    }

    fun eliminar(actividad: ActividadFormativa) {
        viewModelScope.launch {
            repository.eliminar(actividad)
        }
    }
}