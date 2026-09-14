package com.sofia.miformacionctma.data.local

import androidx.room3.migration.Migration

val MIGRATION_1_2 = Migration(1, 2) { connection ->
    connection.prepare(
        "ALTER TABLE reportes ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0"
    )
    connection.prepare(
        "CREATE TABLE IF NOT EXISTS actividades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "titulo TEXT NOT NULL, " +
                "descripcion TEXT, " +
                "progreso INTEGER NOT NULL, " +
                "diasRestantes INTEGER NOT NULL, " +
                "prioridad TEXT NOT NULL, " +
                "fecha TEXT NOT NULL" +
                ")"
    )
    connection.prepare(
        "CREATE INDEX IF NOT EXISTS index_actividades_titulo ON actividades(titulo)"
    )
    connection.prepare(
        "CREATE INDEX IF NOT EXISTS index_actividades_fecha ON actividades(fecha)"
    )
}
