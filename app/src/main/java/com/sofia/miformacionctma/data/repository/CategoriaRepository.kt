package com.sofia.miformacionctma.data.repository

import com.sofia.miformacionctma.data.local.dao.CategoriaDao
import com.sofia.miformacionctma.data.local.entity.CategoriaEntity
import kotlinx.coroutines.flow.Flow

class CategoriaRepository(
    private val categoriaDao: CategoriaDao
) {

    fun observarTodas(): Flow<List<CategoriaEntity>> {
        return categoriaDao.observarTodas()
    }

    suspend fun insertar(nombre: String): Long {
        return categoriaDao.insertar(
            CategoriaEntity(nombre = nombre)
        )
    }

    suspend fun obtenerPorId(id: Long): CategoriaEntity? {
        return categoriaDao.obtenerPorId(id)
    }

    suspend fun eliminarPorId(id: Long) {
        categoriaDao.eliminarPorId(id)
    }
}