package com.sofia.miformacionctma.data.repository

import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow

interface ActividadDataSource {
    fun observarTodas(): Flow<List<ActividadFormativa>>
    fun observarConFalloSimulado(): Flow<List<ActividadFormativa>>
    fun observarPorId(id: Long): Flow<ActividadFormativa?>
    fun buscarPorTitulo(texto: String): Flow<List<ActividadFormativa>>
    suspend fun insertar(actividad: ActividadFormativa): Long
    suspend fun actualizar(actividad: ActividadFormativa)
    suspend fun eliminar(actividad: ActividadFormativa)
    suspend fun eliminarTodas()
    suspend fun refresh(): Int = 0
}
