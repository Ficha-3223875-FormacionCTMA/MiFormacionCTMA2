package com.sofia.miformacionctma

import android.content.Context
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Insert
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sofia.miformacionctma.data.local.AppDatabase
import com.sofia.miformacionctma.data.local.CategoriaEntity
import com.sofia.miformacionctma.data.local.ReporteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDaoTest {
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder<AppDatabase>(context)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun crudBusquedaYRelacionFuncionan() = runBlocking {
        database.categoriaDao().insertar(CategoriaEntity("cat-1", "Redes"))
        database.reporteDao().insertar(
            ReporteEntity("r-1", "Sin internet", "cat-1", false)
        )

        val lista = database.reporteDao().observarTodos().first()
        assertEquals(1, lista.size)
        assertEquals("Sin internet", lista.first().titulo)

        val resultado = database.reporteDao().buscarPorTexto("internet").first()
        assertEquals(1, resultado.size)

        val relacion = database.reporteDao().buscarConCategoria("r-1")
        assertNotNull(relacion)
        assertEquals("Redes", relacion?.categoria?.nombre)

        database.reporteDao().actualizar(
            ReporteEntity("r-1", "Sin internet", "cat-1", true)
        )
        assertEquals(true, database.reporteDao().buscarPorId("r-1")?.resuelto)

        database.reporteDao().eliminarPorId("r-1")
        assertFalse(database.reporteDao().buscarPorId("r-1") != null)
    }
}
