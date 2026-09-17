package com.sofia.miformacionctma.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.sofia.miformacionctma.data.local.entity.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(categoria: CategoriaEntity): Long

    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    fun observarTodas(): Flow<List<CategoriaEntity>>

    @Query("SELECT * FROM categorias WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): CategoriaEntity?

    @Query("DELETE FROM categorias WHERE id = :id")
    suspend fun eliminarPorId(id: Long)
}