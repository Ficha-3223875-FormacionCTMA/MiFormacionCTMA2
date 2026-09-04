package com.sofia.miformacionctma.ui.screens

import androidx.lifecycle.ViewModel
import com.sofia.miformacionctma.data.ReporteRepository
import com.sofia.miformacionctma.domain.Reporte
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CrearUiState(
    val titulo: String = "",
    val errorTitulo: String? = null,
    val guardando: Boolean = false,
    val guardadoId: String? = null
)

class CrearReporteViewModel(
    private val repository: ReporteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearUiState())
    val uiState: StateFlow<CrearUiState> = _uiState.asStateFlow()

    fun actualizarTitulo(valor: String) {
        val titulo = valor.take(80)
        _uiState.update { actual ->
            actual.copy(
                titulo = titulo,
                errorTitulo = if (titulo.trim().length >= 4) null else actual.errorTitulo
            )
        }
    }

    fun guardar() {
        val titulo = _uiState.value.titulo.trim()

        when {
            titulo.isBlank() -> {
                _uiState.update { it.copy(errorTitulo = "El título es obligatorio") }
                return
            }
            titulo.length < 4 -> {
                _uiState.update {
                    it.copy(errorTitulo = "El título debe tener al menos 4 caracteres")
                }
                return
            }
        }

        val reporte = Reporte(
            id = UUID.randomUUID().toString(),
            titulo = titulo
        )

        _uiState.update {
            it.copy(guardando = true, errorTitulo = null)
        }

        repository.agregar(reporte)

        _uiState.update {
            it.copy(guardando = false, guardadoId = reporte.id)
        }
    }
}
