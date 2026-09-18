package com.sofia.miformacionctma

import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.domain.estadoActividad
import com.sofia.miformacionctma.domain.validarActividad
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReglasActividadTest {

    @Test
    fun transicion_de_pendiente_a_en_curso() {
        // Arrange
        val progresoInicial = 0
        val progresoEnCurso = 1
        val diasRestantes = 10

        // Act
        val estadoInicial = estadoActividad(
            progresoInicial,
            diasRestantes
        )

        val estadoNuevo = estadoActividad(
            progresoEnCurso,
            diasRestantes
        )

        // Assert
        assertEquals("PENDIENTE", estadoInicial)
        assertEquals("EN CURSO", estadoNuevo)
    }

    @Test
    fun progreso_0_es_un_valor_valido() {
        // Arrange
        val titulo = "Actividad válida"
        val progreso = 0

        // Act
        val errores = validarActividad(
            titulo,
            progreso
        )

        // Assert
        assertTrue(errores.isEmpty())
    }

    @Test
    fun progreso_100_es_un_valor_valido() {
        // Arrange
        val titulo = "Actividad válida"
        val progreso = 100

        // Act
        val errores = validarActividad(
            titulo,
            progreso
        )

        // Assert
        assertTrue(errores.isEmpty())
    }

    @Test
    fun progreso_menor_que_0_es_invalido() {
        // Arrange
        val titulo = "Actividad válida"
        val progreso = -1

        // Act
        val errores = validarActividad(
            titulo,
            progreso
        )

        // Assert
        assertTrue(
            errores.contains(
                "El progreso debe estar entre 0 y 100"
            )
        )
    }

    @Test
    fun progreso_mayor_que_100_es_invalido() {
        // Arrange
        val titulo = "Actividad válida"
        val progreso = 101

        // Act
        val errores = validarActividad(
            titulo,
            progreso
        )

        // Assert
        assertTrue(
            errores.contains(
                "El progreso debe estar entre 0 y 100"
            )
        )
    }

    @Test
    fun progreso_100_genera_estado_completada() {
        // Arrange
        val progreso = 100
        val diasRestantes = 5

        // Act
        val estado = estadoActividad(
            progreso,
            diasRestantes
        )

        // Assert
        assertEquals("COMPLETADA", estado)
    }

    @Test
    fun progreso_100_siempre_es_completada() {
        val estado = estadoActividad(100, -5)

        assertEquals("COMPLETADA", estado)
    }

    @Test
    fun prioridad_alta_es_identificada_como_alta() {
        val resultado = esPrioridadAlta(Prioridad.ALTA)

        assertTrue(resultado)
    }
}
fun esPrioridadAlta(prioridad: Prioridad): Boolean =
    prioridad == Prioridad.ALTA