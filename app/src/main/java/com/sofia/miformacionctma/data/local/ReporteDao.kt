package com.sofia.miformacionctma.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReporteDao {
    @Query("SELECT * FROM reportes ORDER BY titulo COLLATE NOCASE ASC")
    fun observarTodos(): Flow<List<ReporteEntity>>

    @Query("SELECT * FROM reportes WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: String): ReporteEntity?

    @Query("SELECT * FROM reportes WHERE titulo LIKE '%' || :texto || '%' ORDER BY titulo COLLATE NOCASE ASC")
    fun buscarPorTexto(texto: String): Flow<List<ReporteEntity>>

    @Query("SELECT * FROM reportes WHERE categoriaId = :categoriaId ORDER BY titulo COLLATE NOCASE ASC")
    fun buscarPorCategoria(categoriaId: String): Flow<List<ReporteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(reporte: ReporteEntity)

    @Update
    suspend fun actualizar(reporte: ReporteEntity)

    @Delete
    suspend fun eliminar(reporte: ReporteEntity)

    @Query("DELETE FROM reportes WHERE id = :id")
    suspend fun eliminarPorId(id: String)

    @Transaction
    @Query("SELECT * FROM reportes ORDER BY titulo COLLATE NOCASE ASC")
    fun observarReportesConCategoria(): Flow<List<ReporteConCategoria>>

    @Transaction
    @Query("SELECT * FROM reportes WHERE id = :id LIMIT 1")
    suspend fun buscarConCategoria(id: String): ReporteConCategoria?
}

@Dao
interface CategoriaDao {
    @Query("SELECT * FROM categorias ORDER BY nombre COLLATE NOCASE ASC")
    fun observarTodas(): Flow<List<CategoriaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(categoria: CategoriaEntity)

    @Update
    suspend fun actualizar(categoria: CategoriaEntity)

    @Delete
    suspend fun eliminar(categoria: CategoriaEntity)

    @Query("SELECT * FROM categorias WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: String): CategoriaEntity?
}
