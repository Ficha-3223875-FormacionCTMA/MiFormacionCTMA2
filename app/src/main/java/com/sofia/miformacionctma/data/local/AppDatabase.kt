package com.sofia.miformacionctma.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [
        ReporteEntity::class,
        CategoriaEntity::class,
        ActividadEntity::class,
        EvidenciaEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reporteDao(): ReporteDao

    abstract fun categoriaDao(): CategoriaDao

    abstract fun actividadDao(): ActividadDao

    abstract fun evidenciaDao(): EvidenciaDao
}