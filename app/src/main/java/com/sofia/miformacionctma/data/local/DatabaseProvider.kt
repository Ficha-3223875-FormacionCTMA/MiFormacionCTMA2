package com.sofia.miformacionctma.data.local

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun get(context: Context): AppDatabase =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "reportactma.db"
            )
                .setDriver(BundledSQLiteDriver())
                .addMigrations(
                    MIGRATION_1_2,
                    MIGRATION_2_3
                )
                .build()
                .also { INSTANCE = it }
        }
}