package com.sofia.miformacionctma.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sofia.miformacionctma.data.EstadoEvidencia
import com.sofia.miformacionctma.data.Evidencia
import com.sofia.miformacionctma.data.EvidenciaRepository
import com.sofia.miformacionctma.data.RegistroEvidenciaResultado
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface EvidenciaUiState {

    data object Cargando : EvidenciaUiState

    data object SinEvidencia : EvidenciaUiState

    data class ConEvidencia(
        val evidencia: Evidencia
    ) : EvidenciaUiState

    data class Error(
        val mensaje: String
    ) : EvidenciaUiState
}

sealed interface OperacionEvidenciaUiState {

    data object Inactiva : OperacionEvidenciaUiState

    data object EnCurso : OperacionEvidenciaUiState

    data class Exitosa(
        val mensaje: String
    ) : OperacionEvidenciaUiState

    data class Fallida(
        val mensaje: String
    ) : OperacionEvidenciaUiState
}

class EvidenciaViewModel(
    private val repository: EvidenciaRepository
) : ViewModel() {

    private val actividadId =
        MutableStateFlow<Long?>(null)

    private val _operacionUiState =
        MutableStateFlow<OperacionEvidenciaUiState>(
            OperacionEvidenciaUiState.Inactiva
        )

    val operacionUiState: StateFlow<OperacionEvidenciaUiState> =
        _operacionUiState.asStateFlow()

    val uiState: StateFlow<EvidenciaUiState> =
        actividadId
            .flatMapLatest { id ->

                if (id == null) {

                    flowOf(
                        EvidenciaUiState.SinEvidencia
                    )

                } else {

                    repository
                        .observarPorActividad(id)
                        .map { evidencia ->

                            if (evidencia == null) {

                                EvidenciaUiState.SinEvidencia

                            } else {

                                EvidenciaUiState.ConEvidencia(
                                    evidencia
                                )
                            }
                        }
                }
            }
            .catch { throwable ->

                if (throwable is CancellationException) {
                    throw throwable
                }

                emit(
                    EvidenciaUiState.Error(
                        throwable.message
                            ?: "No fue posible cargar la evidencia."
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = EvidenciaUiState.Cargando
            )

    fun cargarActividad(
        id: Long
    ) {
        actividadId.value = id
    }

    fun registrarLocal(
        actividadId: Long,
        uri: String,
        mimeType: String,
        tamanoBytes: Long,
        nombreArchivo: String,
        archivoPropio: Boolean
    ) {

        viewModelScope.launch {

            _operacionUiState.value =
                OperacionEvidenciaUiState.EnCurso

            try {

                when (
                    val resultado =
                        repository.registrarLocal(
                            actividadId = actividadId,
                            uri = uri,
                            mimeType = mimeType,
                            tamanoBytes = tamanoBytes,
                            nombreArchivo = nombreArchivo,
                            archivoPropio = archivoPropio
                        )
                ) {

                    is RegistroEvidenciaResultado.Exitosa -> {

                        _operacionUiState.value =
                            OperacionEvidenciaUiState.Exitosa(
                                "Evidencia guardada correctamente."
                            )
                    }

                    is RegistroEvidenciaResultado.Invalida -> {

                        _operacionUiState.value =
                            OperacionEvidenciaUiState.Fallida(
                                resultado.mensaje
                            )
                    }
                }

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _operacionUiState.value =
                    OperacionEvidenciaUiState.Fallida(
                        e.message
                            ?: "No fue posible guardar la evidencia."
                    )
            }
        }
    }

    fun eliminar(
        actividadId: Long
    ) {

        viewModelScope.launch {

            _operacionUiState.value =
                OperacionEvidenciaUiState.EnCurso

            try {

                repository.eliminar(
                    actividadId
                )

                _operacionUiState.value =
                    OperacionEvidenciaUiState.Exitosa(
                        "Evidencia eliminada."
                    )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                _operacionUiState.value =
                    OperacionEvidenciaUiState.Fallida(
                        e.message
                            ?: "No fue posible eliminar la evidencia."
                    )
            }
        }
    }

    fun actualizarEstado(
        actividadId: Long,
        estado: EstadoEvidencia
    ) {

        viewModelScope.launch {

            repository.actualizarEstado(
                actividadId = actividadId,
                estado = estado
            )
        }
    }
}

class EvidenciaViewModelFactory(
    private val repository: EvidenciaRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                EvidenciaViewModel::class.java
            )
        ) {

            return EvidenciaViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido: ${modelClass.name}"
        )
    }
}