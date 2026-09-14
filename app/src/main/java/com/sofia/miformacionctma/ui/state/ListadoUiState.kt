package com.sofia.miformacionctma.ui.state

import com.sofia.miformacionctma.domain.ActividadFormativa

sealed interface ListadoUiState {
    data object Cargando : ListadoUiState
    data class Contenido(val actividades: List<ActividadFormativa>) : ListadoUiState
    data object Vacio : ListadoUiState
    data class Error(val mensaje: String) : ListadoUiState
}
