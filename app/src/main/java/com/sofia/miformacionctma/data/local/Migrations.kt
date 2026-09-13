package com.sofia.miformacionctma.data.local

import androidx.room3.migration.Migration

val MIGRATION_1_2 = Migration(1, 2) { connection ->
    connection.prepare(
        "ALTER TABLE reportes ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0"
    )
}