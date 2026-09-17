package com.sofia.miformacionctma.data.repository

import com.sofia.miformacionctma.data.local.dao.ActividadDao
import com.sofia.miformacionctma.data.mapper.toDomain
import com.sofia.miformacionctma.data.mapper.toEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val actividadDao: ActividadDao
) {

    fun observarTodas(): Flow<List<ActividadFormativa>> {
        return actividadDao.observarTodas().map { entidades ->
            entidades.map { it.toDomain() }
        }
    }

    fun observarPorId(id: Long): Flow<ActividadFormativa?> {
        return actividadDao.observarPorId(id).map { entidad ->
            entidad?.toDomain()
        }
    }

    fun buscarPorTitulo(texto: String): Flow<List<ActividadFormativa>> {
        return actividadDao.buscarPorTitulo(texto).map { entidades ->
            entidades.map { it.toDomain() }
        }
    }

    suspend fun insertar(actividad: ActividadFormativa): Long {
        return actividadDao.insertar(actividad.toEntity())
    }

    suspend fun actualizar(actividad: ActividadFormativa) {
        actividadDao.actualizar(actividad.toEntity())
    }

    suspend fun eliminar(actividad: ActividadFormativa) {
        actividadDao.eliminar(actividad.toEntity())
    }

    suspend fun eliminarTodas() {
        actividadDao.eliminarTodas()
    }
}