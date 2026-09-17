package com.sofia.miformacionctma

import com.sofia.miformacionctma.domain.estadoActividad
import com.sofia.miformacionctma.domain.validarActividad
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    // Prueba positiva:
    // Verifica que una actividad con datos correctos no genere errores.
    @Test
    fun validarActividad_datosCorrectos_noGeneraErrores() {

        val errores = validarActividad(
            titulo = "Kotlin básico",
            progreso = 80
        )

        assertTrue(errores.isEmpty())
    }

    // Prueba negativa:
    // Verifica que un título vacío y un progreso inválido generen dos errores.
    @Test
    fun validarActividad_datosIncorrectos_generaErrores() {

        val errores = validarActividad(
            titulo = " ",
            progreso = 120
        )

        assertEquals(2, errores.size)

        assertTrue(
            errores.contains("El título es obligatorio")
        )

        assertTrue(
            errores.contains("El progreso debe estar entre 0 y 100")
        )
    }

    // Prueba adicional:
    // Verifica que una actividad con progreso 100 quede completada.
    @Test
    fun estadoActividad_progreso100_devuelveCompletada() {

        val estado = estadoActividad(
            progreso = 100,
            diasRestantes = -2
        )

        assertEquals(
            "COMPLETADA",
            estado
        )
    }
}