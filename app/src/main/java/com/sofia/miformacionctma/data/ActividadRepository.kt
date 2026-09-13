package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.data.local.ActividadDao
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActividadRepository(
    private val dao: ActividadDao
) {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    val actividades: StateFlow<List<ActividadFormativa>> =
        dao.observarTodas()
            .map { lista ->
                lista.map { it.toDomain() }
            }
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun agregar(actividad: ActividadFormativa) {
        scope.launch {
            dao.insertar(actividad.toEntity())
        }
    }

    fun actualizar(actividad: ActividadFormativa) {
        scope.launch {
            dao.actualizar(actividad.toEntity())
        }
    }

    fun eliminar(id: Long) {
        scope.launch {
            dao.eliminarPorId(id)
        }
    }

    suspend fun buscarPorId(id: Long): ActividadFormativa? {
        return dao.buscarPorId(id)?.toDomain()
    }

    fun buscarPorTexto(texto: String): Flow<List<ActividadFormativa>> {
        return dao.buscarPorTexto(texto)
            .map { lista ->
                lista.map { it.toDomain() }
            }
    }
}
