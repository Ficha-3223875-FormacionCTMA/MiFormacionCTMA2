package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.data.local.ActividadDao
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ActividadRepository {
    fun observarTodas(): Flow<List<ActividadFormativa>>
    fun buscarPorTexto(texto: String): Flow<List<ActividadFormativa>>
    suspend fun buscarPorId(id: Long): ActividadFormativa?
    suspend fun agregar(actividad: ActividadFormativa)
    suspend fun actualizar(actividad: ActividadFormativa)
    suspend fun eliminar(id: Long)
}

class RoomActividadRepository(
    private val dao: ActividadDao
) : ActividadRepository {

    override fun observarTodas(): Flow<List<ActividadFormativa>> =
        dao.observarTodas().map { lista -> lista.map { it.toDomain() } }

    override fun buscarPorTexto(texto: String): Flow<List<ActividadFormativa>> =
        dao.buscarPorTexto(texto).map { lista -> lista.map { it.toDomain() } }

    override suspend fun buscarPorId(id: Long): ActividadFormativa? =
        dao.buscarPorId(id)?.toDomain()

    override suspend fun agregar(actividad: ActividadFormativa) {
        dao.insertar(actividad.toEntity())
    }

    override suspend fun actualizar(actividad: ActividadFormativa) {
        dao.actualizar(actividad.toEntity())
    }

    override suspend fun eliminar(id: Long) {
        dao.eliminarPorId(id)
    }
}
