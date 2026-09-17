package com.sofia.miformacionctma.data.local

import android.content.Context
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @Test
    fun migracion1a2_conservaDatos_yAgregaResueltoFalse() {
        runBlocking {

            val context =
                ApplicationProvider.getApplicationContext<Context>()

            val databaseFile = File(
                context.filesDir,
                "migration-manual-test.db"
            )

            // Eliminamos una base de prueba anterior.
            if (databaseFile.exists()) {
                databaseFile.delete()
            }

            val driver = BundledSQLiteDriver()

            // ---------------------------------------------------------
            // 1. Creamos manualmente una base equivalente a la versión 1
            // ---------------------------------------------------------
            var connection: SQLiteConnection =
                driver.open(databaseFile.absolutePath)

            connection.execSQL(
                """
            CREATE TABLE IF NOT EXISTS categorias (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombre TEXT NOT NULL
            )
            """.trimIndent()
            )

            connection.execSQL(
                """
            CREATE TABLE IF NOT EXISTS actividades (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                progreso INTEGER NOT NULL,
                diasRestantes INTEGER NOT NULL,
                prioridad TEXT NOT NULL,
                categoriaId INTEGER,
                FOREIGN KEY(categoriaId)
                    REFERENCES categorias(id)
                    ON DELETE SET NULL
            )
            """.trimIndent()
            )

            // ---------------------------------------------------------
            // 2. Insertamos un registro antes de la migración
            // ---------------------------------------------------------
            connection.execSQL(
                """
            INSERT INTO actividades (
                titulo,
                descripcion,
                progreso,
                diasRestantes,
                prioridad,
                categoriaId
            )
            VALUES (
                'Actividad antes de migrar',
                'Registro creado en versión 1',
                50,
                5,
                'MEDIA',
                NULL
            )
            """.trimIndent()
            )

            // ---------------------------------------------------------
            // 3. Ejecutamos nuestra migración real 1 -> 2
            // ---------------------------------------------------------
            DatabaseProvider.MIGRATION_1_2.migrate(connection)

            // ---------------------------------------------------------
            // 4. Verificamos que los datos anteriores siguen existiendo
            //    y que resuelto tiene el valor false por defecto.
            // ---------------------------------------------------------
            connection.prepare(
                """
            SELECT titulo, progreso, resuelto
            FROM actividades
            WHERE id = 1
            """.trimIndent()
            ).use { statement ->

                assertTrue(statement.step())

                assertEquals(
                    "Actividad antes de migrar",
                    statement.getText(0)
                )

                assertEquals(
                    50L,
                    statement.getLong(1)
                )

                val resuelto = statement.getLong(2) != 0L

                assertFalse(resuelto)

                // Solo debe existir el registro insertado.
                assertFalse(statement.step())
            }

            connection.close()

            // ---------------------------------------------------------
            // 5. Abrimos nuevamente la BD para comprobar persistencia
            // ---------------------------------------------------------
            connection = driver.open(databaseFile.absolutePath)

            connection.prepare(
                """
            SELECT titulo, resuelto
            FROM actividades
            WHERE id = 1
            """.trimIndent()
            ).use { statement ->

                assertTrue(statement.step())

                assertEquals(
                    "Actividad antes de migrar",
                    statement.getText(0)
                )

                val resuelto = statement.getLong(1) != 0L

                assertFalse(resuelto)
            }

            connection.close()

            databaseFile.delete()
        }
    }
}