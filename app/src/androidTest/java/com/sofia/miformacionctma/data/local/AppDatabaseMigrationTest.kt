package com.sofia.miformacionctma.data.local

import androidx.room3.testing.MigrationTestHelper
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseMigrationTest {

    private val instrumentation =
        InstrumentationRegistry.getInstrumentation()

    private val testDb = "migration-test.db"

    @get:Rule
    val helper = MigrationTestHelper(
        instrumentation = instrumentation,
        file = instrumentation.targetContext.getDatabasePath(testDb),
        driver = BundledSQLiteDriver(),
        databaseClass = AppDatabase::class
    )

    @Test
    fun migracion_2_3_crea_evidencias_y_conserva_actividades() = runTest {

        val dbVersion2 = helper.createDatabase(2)

        dbVersion2.prepare(
            """
            INSERT INTO actividades (
                id,
                titulo,
                descripcion,
                progreso,
                diasRestantes,
                prioridad,
                fecha
            )
            VALUES (
                1,
                'Actividad existente',
                'Actividad creada antes de la migración',
                50,
                5,
                'ALTA',
                '2026-09-21'
            )
            """.trimIndent()
        ).use { statement ->
            statement.step()
        }

        dbVersion2.close()

        val dbVersion3 =
            helper.runMigrationsAndValidate(
                3,
                listOf(MIGRATION_2_3)
            )

        val titulo =
            dbVersion3.prepare(
                """
                SELECT titulo
                FROM actividades
                WHERE id = 1
                """.trimIndent()
            ).use { statement ->
                assertTrue(statement.step())
                statement.getText(0)
            }

        assertEquals(
            "Actividad existente",
            titulo
        )

        dbVersion3.prepare(
            """
            INSERT INTO evidencias (
                actividadId,
                uri,
                mimeType,
                tamanoBytes,
                nombreArchivo,
                estado,
                archivoPropio
            )
            VALUES (
                1,
                'content://miformacion/evidencia/1',
                'image/jpeg',
                2048,
                'evidencia_1.jpg',
                'LOCAL',
                0
            )
            """.trimIndent()
        ).use { statement ->
            statement.step()
        }

        val uri =
            dbVersion3.prepare(
                """
                SELECT uri
                FROM evidencias
                WHERE actividadId = 1
                """.trimIndent()
            ).use { statement ->
                assertTrue(statement.step())
                statement.getText(0)
            }

        assertEquals(
            "content://miformacion/evidencia/1",
            uri
        )

        dbVersion3.close()
    }
}