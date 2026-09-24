package com.sofia.miformacionctma.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import com.sofia.miformacionctma.data.local.entity.ActividadConCategoria
import com.sofia.miformacionctma.data.local.entity.ActividadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(actividad: ActividadEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(actividades: List<ActividadEntity>)

    @Update
    suspend fun actualizar(actividad: ActividadEntity)

    @Delete
    suspend fun eliminar(actividad: ActividadEntity)

    @Query(
        """
        SELECT * FROM actividades
        ORDER BY diasRestantes ASC, progreso ASC
        """
    )
    fun observarTodas(): Flow<List<ActividadEntity>>

    @Query("SELECT * FROM actividades WHERE id = :id LIMIT 1")
    fun observarPorId(id: Long): Flow<ActividadEntity?>

    @Query(
        """
        SELECT * FROM actividades
        WHERE titulo LIKE '%' || :texto || '%'
        ORDER BY diasRestantes ASC, progreso ASC
        """
    )
    fun buscarPorTitulo(texto: String): Flow<List<ActividadEntity>>

    @Transaction
    @Query(
        """
        SELECT * FROM actividades
        ORDER BY diasRestantes ASC, progreso ASC
        """
    )
    fun observarActividadesConCategoria(): Flow<List<ActividadConCategoria>>

    @Query("DELETE FROM actividades")
    suspend fun eliminarTodas()

    @Transaction
    suspend fun reemplazarTodas(actividades: List<ActividadEntity>) {
        eliminarTodas()
        insertarTodas(actividades)
    }
}