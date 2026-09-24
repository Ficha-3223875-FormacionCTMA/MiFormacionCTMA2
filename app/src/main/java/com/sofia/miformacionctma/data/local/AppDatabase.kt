package com.sofia.miformacionctma.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.sofia.miformacionctma.data.local.dao.ActividadDao
import com.sofia.miformacionctma.data.local.dao.CategoriaDao
import com.sofia.miformacionctma.data.local.dao.EvidenciaDao
import com.sofia.miformacionctma.data.local.entity.ActividadEntity
import com.sofia.miformacionctma.data.local.entity.CategoriaEntity
import com.sofia.miformacionctma.data.local.entity.EvidenciaEntity

@Database(
    entities = [ActividadEntity::class, CategoriaEntity::class, EvidenciaEntity::class],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actividadDao(): ActividadDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun evidenciaDao(): EvidenciaDao
}
