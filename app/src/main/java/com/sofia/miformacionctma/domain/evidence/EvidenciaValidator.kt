package com.sofia.miformacionctma.domain.evidence

object EvidenciaValidator {
    const val MAX_BYTES = 5L * 1024L * 1024L
    fun validar(mimeType: String?, tamanoBytes: Long) {
        require(!mimeType.isNullOrBlank() && mimeType.startsWith("image/")) { "Solo se permiten imágenes" }
        require(tamanoBytes in 1..MAX_BYTES) { "La imagen debe pesar entre 1 byte y 5 MB" }
    }
}
