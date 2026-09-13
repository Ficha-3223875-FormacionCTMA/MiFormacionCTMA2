package com.sofia.miformacionctma

import android.content.Context
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Insert
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sofia.miformacionctma.data.local.AppDatabase
import com.sofia.miformacionctma.data.local.DatabaseProvider
import com.sofia.miformacionctma.data.local.ReporteEntity
import com.sofia.miformacionctma.data.local.MIGRATION_1_2
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@Entity(tableName = "categorias")
data class CategoriaEntityV1(
    @PrimaryKey val id: String,
    val nombre: String
)

@Entity(tableName = "reportes")
data class ReporteEntityV1(
    @PrimaryKey val id: String,
    val titulo: String,
    val categoriaId: String? = null
)

@Dao
interface V1Dao {
    @Insert
    suspend fun insertar(reporte: ReporteEntityV1)
}

@Database(entities = [ReporteEntityV1::class, CategoriaEntityV1::class], version = 1)
abstract class DatabaseV1 : RoomDatabase() {
    abstract fun dao(): V1Dao
}

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    @Test
    fun migracionUnoADosConservaReporteYAgregaResueltoFalse() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val name = "migration-test.db"

        context.deleteDatabase(name)

        val v1 = Room.databaseBuilder(context, DatabaseV1::class.java, name)
            .setDriver(BundledSQLiteDriver())
            .build()
        v1.dao().insertar(ReporteEntityV1("r-1", "Reporte previo", null))
        v1.close()

        val v2 = Room.databaseBuilder(context, AppDatabase::class.java, name)
            .setDriver(BundledSQLiteDriver())
            .addMigrations(MIGRATION_1_2)
            .build()

        val reporte = v2.reporteDao().buscarPorId("r-1")
        assertEquals("Reporte previo", reporte?.titulo)
        assertFalse(reporte?.resuelto ?: true)

        v2.close()
        context.deleteDatabase(name)
    }
}
