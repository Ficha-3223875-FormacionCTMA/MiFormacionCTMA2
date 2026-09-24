package com.sofia.miformacionctma.domain.evidence

import org.junit.Assert.assertTrue
import org.junit.Test

class EvidenciaValidatorTest {
    @Test fun imagenValida_esAceptada() { EvidenciaValidator.validar("image/jpeg", 1024) }
    @Test fun mimeNoImagen_esRechazado() { assertTrue(runCatching { EvidenciaValidator.validar("application/pdf", 100) }.isFailure) }
    @Test fun archivoVacio_esRechazado() { assertTrue(runCatching { EvidenciaValidator.validar("image/png", 0) }.isFailure) }
    @Test fun archivoMayorA5MB_esRechazado() { assertTrue(runCatching { EvidenciaValidator.validar("image/png", EvidenciaValidator.MAX_BYTES + 1) }.isFailure) }
}
