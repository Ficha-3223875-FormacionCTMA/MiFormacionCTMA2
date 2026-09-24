package com.sofia.miformacionctma

import com.sofia.miformacionctma.domain.TAMANO_MAXIMO_EVIDENCIA_BYTES
import com.sofia.miformacionctma.domain.ValidacionEvidencia
import com.sofia.miformacionctma.domain.validarEvidencia
import org.junit.Assert.assertTrue
import org.junit.Test

class ReglasEvidenciaTest {

    @Test
    fun evidenciaValidaEsAceptada() {
        val resultado = validarEvidencia(
            actividadId = 1,
            uri = "content://miformacion/evidencia/1",
            mimeType = "image/jpeg",
            tamanoBytes = 2048,
            nombreArchivo = "evidencia_1.jpg"
        )

        assertTrue(resultado is ValidacionEvidencia.Valida)
    }

    @Test
    fun archivoQueNoEsImagenEsRechazado() {
        val resultado = validarEvidencia(
            actividadId = 1,
            uri = "content://miformacion/documento/1",
            mimeType = "application/pdf",
            tamanoBytes = 2048,
            nombreArchivo = "documento.pdf"
        )

        assertTrue(resultado is ValidacionEvidencia.Invalida)
    }

    @Test
    fun imagenMayorA5MbEsRechazada() {
        val resultado = validarEvidencia(
            actividadId = 1,
            uri = "content://miformacion/evidencia/2",
            mimeType = "image/jpeg",
            tamanoBytes = TAMANO_MAXIMO_EVIDENCIA_BYTES + 1,
            nombreArchivo = "imagen_grande.jpg"
        )

        assertTrue(resultado is ValidacionEvidencia.Invalida)
    }

    @Test
    fun uriQueNoEsContentEsRechazada() {
        val resultado = validarEvidencia(
            actividadId = 1,
            uri = "file:///storage/emulated/0/foto.jpg",
            mimeType = "image/jpeg",
            tamanoBytes = 2048,
            nombreArchivo = "foto.jpg"
        )

        assertTrue(resultado is ValidacionEvidencia.Invalida)
    }

    @Test
    fun tamanoCeroEsRechazado() {
        val resultado = validarEvidencia(
            actividadId = 1,
            uri = "content://miformacion/evidencia/3",
            mimeType = "image/png",
            tamanoBytes = 0,
            nombreArchivo = "foto.png"
        )

        assertTrue(resultado is ValidacionEvidencia.Invalida)
    }

    @Test
    fun nombreVacioEsRechazado() {
        val resultado = validarEvidencia(
            actividadId = 1,
            uri = "content://miformacion/evidencia/4",
            mimeType = "image/png",
            tamanoBytes = 2048,
            nombreArchivo = ""
        )

        assertTrue(resultado is ValidacionEvidencia.Invalida)
    }

    @Test
    fun actividadInvalidaEsRechazada() {
        val resultado = validarEvidencia(
            actividadId = 0,
            uri = "content://miformacion/evidencia/5",
            mimeType = "image/jpeg",
            tamanoBytes = 2048,
            nombreArchivo = "foto.jpg"
        )

        assertTrue(resultado is ValidacionEvidencia.Invalida)
    }
}