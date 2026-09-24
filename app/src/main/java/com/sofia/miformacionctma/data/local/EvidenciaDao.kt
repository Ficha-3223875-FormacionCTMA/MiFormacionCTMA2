package com.sofia.miformacionctma.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenciaDao {

    @Query(
        "SELECT * FROM evidencias " +
                "WHERE actividadId = :actividadId " +
                "LIMIT 1"
    )
    fun observarPorActividad(
        actividadId: Long
    ): Flow<EvidenciaEntity?>

    @Query(
        "SELECT * FROM evidencias " +
                "WHERE actividadId = :actividadId " +
                "LIMIT 1"
    )
    suspend fun buscarPorActividad(
        actividadId: Long
    ): EvidenciaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(
        evidencia: EvidenciaEntity
    ): Long

    @Update
    suspend fun actualizar(
        evidencia: EvidenciaEntity
    )

    @Delete
    suspend fun eliminar(
        evidencia: EvidenciaEntity
    )

    @Query(
        "DELETE FROM evidencias " +
                "WHERE actividadId = :actividadId"
    )
    suspend fun eliminarPorActividad(
        actividadId: Long
    )
}