package com.sofia.miformacionctma.data.local

import androidx.room3.migration.Migration

val MIGRATION_1_2 = Migration(1, 2) { connection ->

    // ---------------------------------------------------------
    // 1. Agregar el índice único que CategoriaEntity
    //    requiere desde la versión 2.
    // ---------------------------------------------------------

    connection.prepare(
        """
        CREATE UNIQUE INDEX IF NOT EXISTS index_categorias_nombre
        ON categorias(nombre)
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    // ---------------------------------------------------------
    // 2. Crear una nueva tabla reportes con el esquema
    //    exacto esperado por Room en la versión 2.
    // ---------------------------------------------------------

    connection.prepare(
        """
        CREATE TABLE IF NOT EXISTS reportes_nuevo (
            id TEXT NOT NULL,
            titulo TEXT NOT NULL,
            categoriaId TEXT,
            resuelto INTEGER NOT NULL,
            PRIMARY KEY(id),
            FOREIGN KEY(categoriaId)
                REFERENCES categorias(id)
                ON UPDATE NO ACTION
                ON DELETE SET NULL
        )
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    // ---------------------------------------------------------
    // 3. Conservar los reportes existentes.
    //    Los registros antiguos reciben resuelto = 0 (false).
    // ---------------------------------------------------------

    connection.prepare(
        """
        INSERT INTO reportes_nuevo (
            id,
            titulo,
            categoriaId,
            resuelto
        )
        SELECT
            id,
            titulo,
            categoriaId,
            0
        FROM reportes
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    // ---------------------------------------------------------
    // 4. Sustituir la tabla antigua.
    // ---------------------------------------------------------

    connection.prepare(
        "DROP TABLE reportes"
    ).use { statement ->
        statement.step()
    }

    connection.prepare(
        "ALTER TABLE reportes_nuevo RENAME TO reportes"
    ).use { statement ->
        statement.step()
    }

    // ---------------------------------------------------------
    // 5. Crear los índices esperados por ReporteEntity.
    // ---------------------------------------------------------

    connection.prepare(
        """
        CREATE INDEX IF NOT EXISTS index_reportes_titulo
        ON reportes(titulo)
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    connection.prepare(
        """
        CREATE INDEX IF NOT EXISTS index_reportes_categoriaId
        ON reportes(categoriaId)
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    // ---------------------------------------------------------
    // 6. Crear la tabla actividades de la versión 2.
    // ---------------------------------------------------------

    connection.prepare(
        """
        CREATE TABLE IF NOT EXISTS actividades (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            titulo TEXT NOT NULL,
            descripcion TEXT,
            progreso INTEGER NOT NULL,
            diasRestantes INTEGER NOT NULL,
            prioridad TEXT NOT NULL,
            fecha TEXT NOT NULL
        )
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    connection.prepare(
        """
        CREATE INDEX IF NOT EXISTS index_actividades_titulo
        ON actividades(titulo)
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    connection.prepare(
        """
        CREATE INDEX IF NOT EXISTS index_actividades_fecha
        ON actividades(fecha)
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }
}

val MIGRATION_2_3 = Migration(2, 3) { connection ->

    connection.prepare(
        """
        CREATE TABLE IF NOT EXISTS evidencias (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            actividadId INTEGER NOT NULL,
            uri TEXT NOT NULL,
            mimeType TEXT NOT NULL,
            tamanoBytes INTEGER NOT NULL,
            nombreArchivo TEXT NOT NULL,
            estado TEXT NOT NULL,
            archivoPropio INTEGER NOT NULL,
            FOREIGN KEY(actividadId)
                REFERENCES actividades(id)
                ON UPDATE NO ACTION
                ON DELETE CASCADE
        )
        """.trimIndent()
    ).use { statement ->
        statement.step()
    }

    connection.prepare(
        """
CREATE UNIQUE INDEX IF NOT EXISTS index_evidencias_actividadId
ON evidencias(actividadId)
""".trimIndent()
    ).use { statement ->
        statement.step()
    }
}