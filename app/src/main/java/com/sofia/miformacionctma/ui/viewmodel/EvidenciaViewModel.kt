package com.sofia.miformacionctma.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofia.miformacionctma.data.local.entity.EvidenciaEntity
import com.sofia.miformacionctma.data.repository.EvidenciaRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface EvidenciaUiState {
    data object Inactiva : EvidenciaUiState
    data object Procesando : EvidenciaUiState
    data class Mensaje(val texto: String) : EvidenciaUiState
    data class Error(val texto: String) : EvidenciaUiState
}

class EvidenciaViewModel(private val repository: EvidenciaRepository) : ViewModel() {
    val evidencias: StateFlow<List<EvidenciaEntity>> = repository.observarTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    private val _estado = kotlinx.coroutines.flow.MutableStateFlow<EvidenciaUiState>(EvidenciaUiState.Inactiva)
    val estado: StateFlow<EvidenciaUiState> = _estado

    fun guardar(actividadId: Long, uri: Uri) = viewModelScope.launch {
        _estado.value = EvidenciaUiState.Procesando
        val result = repository.guardarLocal(actividadId, uri)
        _estado.value = result.fold(
            onSuccess = { EvidenciaUiState.Mensaje("Evidencia guardada localmente") },
            onFailure = { EvidenciaUiState.Error(it.message ?: "No fue posible guardar la evidencia") }
        )
    }

    fun eliminar(actividadId: Long) = viewModelScope.launch {
        repository.eliminar(actividadId)
        _estado.value = EvidenciaUiState.Mensaje("Evidencia eliminada")
    }

    fun subir(evidencia: EvidenciaEntity) = viewModelScope.launch {
        _estado.value = EvidenciaUiState.Procesando
        try {
            repository.subir(evidencia)
            _estado.value = EvidenciaUiState.Mensaje("Evidencia sincronizada")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _estado.value = EvidenciaUiState.Error("La evidencia queda guardada localmente. Puedes reintentar.")
        }
    }
}
