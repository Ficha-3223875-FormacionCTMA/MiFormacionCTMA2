package com.sofia.miformacionctma

import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.domain.estadoActividad
import com.sofia.miformacionctma.domain.progresoEsValidoParaCompletar
import com.sofia.miformacionctma.domain.validarActividad
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReglasActividadTest {

    @Test
    fun transicion_de_pendiente_a_en_curso() {
        val progresoInicial = 0
        val progresoEnCurso = 1
        val diasRestantes = 10

        val estadoInicial = estadoActividad(
            progresoInicial,
            diasRestantes
        )

        val estadoNuevo = estadoActividad(
            progresoEnCurso,
            diasRestantes
        )

        assertEquals("PENDIENTE", estadoInicial)
        assertEquals("EN CURSO", estadoNuevo)
    }

    @Test
    fun progreso_0_es_un_valor_valido() {
        val titulo = "Actividad válida"
        val progreso = 0

        val errores = validarActividad(
            titulo,
            progreso
        )

        assertTrue(errores.isEmpty())
    }

    @Test
    fun progreso_100_es_un_valor_valido() {
        val titulo = "Actividad válida"
        val progreso = 100

        val errores = validarActividad(
            titulo,
            progreso
        )

        assertTrue(errores.isEmpty())
    }

    @Test
    fun progreso_menor_que_0_es_invalido() {
        val titulo = "Actividad válida"
        val progreso = -1

        val errores = validarActividad(
            titulo,
            progreso
        )

        assertTrue(
            errores.contains(
                "El progreso debe estar entre 0 y 100"
            )
        )
    }

    @Test
    fun progreso_mayor_que_100_es_invalido() {
        val titulo = "Actividad válida"
        val progreso = 101

        val errores = validarActividad(
            titulo,
            progreso
        )

        assertTrue(
            errores.contains(
                "El progreso debe estar entre 0 y 100"
            )
        )
    }

    @Test
    fun progreso_100_genera_estado_completada() {
        val progreso = 100
        val diasRestantes = 5

        val estado = estadoActividad(
            progreso,
            diasRestantes
        )

        assertEquals("COMPLETADA", estado)
    }

    @Test
    fun progreso_100_siempre_es_completada() {
        val progreso = 100
        val diasRestantes = -5

        val estado = estadoActividad(
            progreso,
            diasRestantes
        )

        assertEquals("COMPLETADA", estado)
    }

    @Test
    fun prioridad_alta_es_identificada_como_alta() {
        val prioridad = Prioridad.ALTA

        val resultado = esPrioridadAlta(prioridad)

        assertTrue(resultado)
    }

    @Test
    fun solo_el_progreso_100_permite_completar() {
        val progresoCompletado = 100
        val progresoNoCompletado = 99

        val resultadoCompletado =
            progresoEsValidoParaCompletar(progresoCompletado)

        val resultadoNoCompletado =
            progresoEsValidoParaCompletar(progresoNoCompletado)

        assertTrue(resultadoCompletado)
        assertTrue(!resultadoNoCompletado)
    }
}

fun esPrioridadAlta(prioridad: Prioridad): Boolean =
    prioridad == Prioridad.ALTA