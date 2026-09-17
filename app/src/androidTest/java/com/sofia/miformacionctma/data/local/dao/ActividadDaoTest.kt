package com.sofia.miformacionctma.data.local.dao

import android.content.Context
import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sofia.miformacionctma.data.local.AppDatabase
import com.sofia.miformacionctma.data.local.entity.ActividadEntity
import com.sofia.miformacionctma.data.local.entity.CategoriaEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActividadDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var actividadDao: ActividadDao
    private lateinit var categoriaDao: CategoriaDao

    @Before
    fun prepararBaseDeDatos() {
        val context =
            ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder<AppDatabase>(
            context
        ).build()

        actividadDao = database.actividadDao()
        categoriaDao = database.categoriaDao()
    }

    @After
    fun cerrarBaseDeDatos() {
        database.close()
    }

    @Test
    fun insertarActividad_yConsultarPorId() = runBlocking {

        val actividad = ActividadEntity(
            titulo = "Prueba DAO",
            descripcion = "Actividad creada desde prueba instrumentada",
            progreso = 50,
            diasRestantes = 5,
            prioridad = "MEDIA",
            categoriaId = null,
            resuelto = false
        )

        val idGenerado = actividadDao.insertar(actividad)

        val actividadGuardada =
            actividadDao.observarPorId(idGenerado).first()

        assertNotNull(actividadGuardada)
        assertEquals("Prueba DAO", actividadGuardada?.titulo)
        assertEquals(50, actividadGuardada?.progreso)
        assertEquals(false, actividadGuardada?.resuelto)
    }

    @Test
    fun actualizarActividad() = runBlocking {

        val actividad = ActividadEntity(
            titulo = "Actividad original",
            descripcion = "Antes de actualizar",
            progreso = 20,
            diasRestantes = 10,
            prioridad = "BAJA",
            categoriaId = null,
            resuelto = false
        )

        val idGenerado = actividadDao.insertar(actividad)

        val actualizada = actividad.copy(
            id = idGenerado,
            titulo = "Actividad actualizada",
            progreso = 80
        )

        actividadDao.actualizar(actualizada)

        val resultado =
            actividadDao.observarPorId(idGenerado).first()

        assertNotNull(resultado)
        assertEquals("Actividad actualizada", resultado?.titulo)
        assertEquals(80, resultado?.progreso)
    }

    @Test
    fun eliminarActividad() = runBlocking {

        val actividad = ActividadEntity(
            titulo = "Actividad para eliminar",
            descripcion = null,
            progreso = 10,
            diasRestantes = 3,
            prioridad = "MEDIA",
            categoriaId = null,
            resuelto = false
        )

        val idGenerado = actividadDao.insertar(actividad)

        actividadDao.eliminar(
            actividad.copy(id = idGenerado)
        )

        val resultado =
            actividadDao.observarPorId(idGenerado).first()

        assertEquals(null, resultado)
    }

    @Test
    fun buscarActividadPorTitulo() = runBlocking {

        actividadDao.insertar(
            ActividadEntity(
                titulo = "Aprender Room",
                descripcion = "Persistencia local",
                progreso = 70,
                diasRestantes = 4,
                prioridad = "ALTA",
                categoriaId = null,
                resuelto = false
            )
        )

        actividadDao.insertar(
            ActividadEntity(
                titulo = "Practicar Kotlin",
                descripcion = "Funciones y colecciones",
                progreso = 40,
                diasRestantes = 7,
                prioridad = "MEDIA",
                categoriaId = null,
                resuelto = false
            )
        )

        val resultados =
            actividadDao.buscarPorTitulo("Room").first()

        assertEquals(1, resultados.size)
        assertEquals("Aprender Room", resultados.first().titulo)
    }

    @Test
    fun eliminarTodasLasActividades() = runBlocking {

        actividadDao.insertar(
            ActividadEntity(
                titulo = "Actividad 1",
                descripcion = null,
                progreso = 20,
                diasRestantes = 2,
                prioridad = "MEDIA",
                categoriaId = null,
                resuelto = false
            )
        )

        actividadDao.insertar(
            ActividadEntity(
                titulo = "Actividad 2",
                descripcion = null,
                progreso = 60,
                diasRestantes = 5,
                prioridad = "ALTA",
                categoriaId = null,
                resuelto = false
            )
        )

        actividadDao.eliminarTodas()

        val actividades =
            actividadDao.observarTodas().first()

        assertTrue(actividades.isEmpty())
    }

    @Test
    fun obtenerActividadConCategoria() = runBlocking {

        val categoriaId = categoriaDao.insertar(
            CategoriaEntity(
                nombre = "Desarrollo móvil"
            )
        )

        actividadDao.insertar(
            ActividadEntity(
                titulo = "Persistencia con Room",
                descripcion = "Prueba de relación entre actividad y categoría",
                progreso = 75,
                diasRestantes = 3,
                prioridad = "ALTA",
                categoriaId = categoriaId,
                resuelto = false
            )
        )

        val resultados =
            actividadDao.observarActividadesConCategoria().first()

        assertEquals(1, resultados.size)

        val resultado = resultados.first()

        assertEquals(
            "Persistencia con Room",
            resultado.actividad.titulo
        )

        assertNotNull(resultado.categoria)

        assertEquals(
            "Desarrollo móvil",
            resultado.categoria?.nombre
        )

        assertEquals(
            categoriaId,
            resultado.actividad.categoriaId
        )
    }
}