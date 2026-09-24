package com.sofia.miformacionctma.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.sofia.miformacionctma.data.local.entity.EvidenciaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(evidencia: EvidenciaEntity): Long

    @Query("SELECT * FROM evidencias WHERE actividadId = :actividadId LIMIT 1")
    fun observar(actividadId: Long): Flow<EvidenciaEntity?>

    @Query("SELECT * FROM evidencias WHERE actividadId = :actividadId LIMIT 1")
    suspend fun obtener(actividadId: Long): EvidenciaEntity?

    @Query("SELECT * FROM evidencias ORDER BY actividadId")
    fun observarTodas(): Flow<List<EvidenciaEntity>>

    @Query("UPDATE evidencias SET estado = :estado WHERE actividadId = :actividadId")
    suspend fun cambiarEstado(actividadId: Long, estado: String)

    @Query("DELETE FROM evidencias WHERE actividadId = :actividadId")
    suspend fun eliminar(actividadId: Long)
}
