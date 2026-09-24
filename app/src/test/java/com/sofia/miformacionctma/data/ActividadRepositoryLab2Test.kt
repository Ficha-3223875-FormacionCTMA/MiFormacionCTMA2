package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.data.local.ActividadDao
import com.sofia.miformacionctma.data.local.ActividadEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Lab 2 - Fixtures, Stubs y Mocks
 *
 * Estos dobles de prueba permiten probar el repositorio
 * sin utilizar una base de datos Room real.
 */
class ActividadRepositoryLab2Test {

    /**
     * FIXTURE / FACTORY
     *
     * Crea una actividad sintética reutilizable para las pruebas.
     */
    private fun crearActividadDePrueba(
        id: Long = 1L,
        titulo: String = "Actividad de prueba",
        progreso: Int = 50
    ): ActividadFormativa {
        return ActividadFormativa(
            id = id,
            titulo = titulo,
            descripcion = "Descripción sintética para pruebas",
            progreso = progreso,
            diasRestantes = 5,
            prioridad = Prioridad.MEDIA,
            fecha = "2026-09-17"
        )
    }

    /**
     * MOCK / FAKE MANUAL
     *
     * Simula el DAO y permite verificar las operaciones
     * realizadas por el repositorio.
     */
    private class FakeActividadDao : ActividadDao {

        var insertarLlamadas = 0
        var buscarPorIdLlamadas = 0
        var actualizarLlamadas = 0

        var ultimaActividadInsertada: ActividadEntity? = null

        var resultadoBusqueda: ActividadEntity? = null

        var errorAlActualizar: Boolean = false

        override fun observarTodas(): Flow<List<ActividadEntity>> {
            return emptyFlow()
        }

        override suspend fun buscarPorId(id: Long): ActividadEntity? {
            buscarPorIdLlamadas++

            return resultadoBusqueda
        }

        override fun buscarPorTexto(
            texto: String
        ): Flow<List<ActividadEntity>> {
            return emptyFlow()
        }

        override suspend fun insertar(
            actividad: ActividadEntity
        ): Long {
            insertarLlamadas++
            ultimaActividadInsertada = actividad

            return actividad.id
        }

        override suspend fun insertarTodas(
            actividades: List<ActividadEntity>
        ) {
            actividades.forEach {
                insertar(it)
            }
        }

        override suspend fun guardarTodasDesdeServidor(
            actividades: List<ActividadEntity>
        ) {
            insertarTodas(actividades)
        }

        override suspend fun actualizar(
            actividad: ActividadEntity
        ) {
            actualizarLlamadas++

            if (errorAlActualizar) {
                throw IllegalStateException(
                    "Error simulado durante la actualización"
                )
            }
        }

        override suspend fun eliminar(
            actividad: ActividadEntity
        ) {
        }

        override suspend fun eliminarPorId(
            id: Long
        ) {
        }
    }

    @Test
    fun agregar_actividad_utiliza_el_dao() {
        // Arrange
        val dao = FakeActividadDao()
        val repositorio = RoomActividadRepository(dao)
        val actividad = crearActividadDePrueba()

        // Act
        kotlinx.coroutines.runBlocking {
            repositorio.agregar(actividad)
        }

        // Assert
        assertEquals(1, dao.insertarLlamadas)
        assertNotNull(dao.ultimaActividadInsertada)
        assertEquals(
            "Actividad de prueba",
            dao.ultimaActividadInsertada?.titulo
        )
    }

    @Test
    fun buscar_actividad_inexistente_devuelve_null() {
        // Arrange
        val dao = FakeActividadDao()

        // STUB:
        // indicamos explícitamente que la búsqueda no encuentra
        // ninguna actividad.
        dao.resultadoBusqueda = null

        val repositorio = RoomActividadRepository(dao)

        // Act
        val resultado = kotlinx.coroutines.runBlocking {
            repositorio.buscarPorId(999L)
        }

        // Assert
        assertNull(resultado)
        assertEquals(1, dao.buscarPorIdLlamadas)
    }

    @Test
    fun actualizar_propagara_error_del_dao() {
        // Arrange
        val dao = FakeActividadDao()

        // MOCK:
        // simulamos un fallo de persistencia.
        dao.errorAlActualizar = true

        val repositorio = RoomActividadRepository(dao)
        val actividad = crearActividadDePrueba()

        // Act + Assert
        try {
            kotlinx.coroutines.runBlocking {
                repositorio.actualizar(actividad)
            }

            throw AssertionError(
                "Se esperaba una excepción durante la actualización"
            )
        } catch (error: IllegalStateException) {
            assertEquals(
                "Error simulado durante la actualización",
                error.message
            )
        }

        assertEquals(1, dao.actualizarLlamadas)
    }
}
