package com.sofia.miformacionctma.data.local

import android.content.Context
import androidx.room3.Room
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    val MIGRATION_1_2 = object : Migration(1, 2) {

        override suspend fun migrate(
            connection: SQLiteConnection
        ) {
            connection.execSQL(
                """
                ALTER TABLE actividades
                ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0
                """.trimIndent()
            )
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override suspend fun migrate(connection: SQLiteConnection) {
            connection.execSQL("""
                CREATE TABLE IF NOT EXISTS evidencias (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    actividadId INTEGER NOT NULL,
                    uri TEXT NOT NULL,
                    mimeType TEXT NOT NULL,
                    tamanoBytes INTEGER NOT NULL,
                    estado TEXT NOT NULL,
                    FOREIGN KEY(actividadId) REFERENCES actividades(id) ON UPDATE NO ACTION ON DELETE CASCADE
                )
            """.trimIndent())
            connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_evidencias_actividadId ON evidencias (actividadId)")
        }
    }

    fun getDatabase(context: Context): AppDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder<AppDatabase>(
                context = context.applicationContext,
                name = "mi_formacion_ctma.db"
            )
                .setDriver(BundledSQLiteDriver())
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

            INSTANCE = instance

            instance
        }
    }
}