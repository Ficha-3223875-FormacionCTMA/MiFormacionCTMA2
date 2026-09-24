package com.sofia.miformacionctma.domain

const val TAMANO_MAXIMO_EVIDENCIA_BYTES: Long =
    5L * 1024L * 1024L

sealed interface ValidacionEvidencia {

    data object Valida : ValidacionEvidencia

    data class Invalida(
        val mensaje: String
    ) : ValidacionEvidencia
}

fun validarEvidencia(
    actividadId: Long,
    uri: String,
    mimeType: String,
    tamanoBytes: Long,
    nombreArchivo: String
): ValidacionEvidencia {

    if (actividadId <= 0) {
        return ValidacionEvidencia.Invalida(
            "La actividad asociada no es válida."
        )
    }

    if (!uri.startsWith("content://")) {
        return ValidacionEvidencia.Invalida(
            "La evidencia debe utilizar una URI de contenido segura."
        )
    }

    if (!mimeType.startsWith("image/")) {
        return ValidacionEvidencia.Invalida(
            "El archivo seleccionado no es una imagen válida."
        )
    }

    if (tamanoBytes <= 0) {
        return ValidacionEvidencia.Invalida(
            "No fue posible determinar el tamaño de la imagen."
        )
    }

    if (tamanoBytes > TAMANO_MAXIMO_EVIDENCIA_BYTES) {
        return ValidacionEvidencia.Invalida(
            "La imagen supera el tamaño máximo permitido de 5 MB."
        )
    }

    if (nombreArchivo.isBlank()) {
        return ValidacionEvidencia.Invalida(
            "El nombre de la evidencia no es válido."
        )
    }

    return ValidacionEvidencia.Valida
}