package com.sofia.miformacionctma.ui.viewmodel

import com.sofia.miformacionctma.domain.ActividadFormativa

sealed interface ListadoUiState {
    data object Cargando : ListadoUiState
    data object Vacio : ListadoUiState
    data class Contenido(
        val actividades: List<ActividadFormativa>
    ) : ListadoUiState

    data class Error(
        val mensaje: String
    ) : ListadoUiState
}

sealed interface OperacionUiState {
    data object Inactiva : OperacionUiState
    data object EnCurso : OperacionUiState

    data class Exitosa(
        val mensaje: String
    ) : OperacionUiState

    data class Fallida(
        val mensaje: String
    ) : OperacionUiState
}