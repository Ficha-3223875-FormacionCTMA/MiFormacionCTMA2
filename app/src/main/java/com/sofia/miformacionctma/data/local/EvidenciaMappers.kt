package com.sofia.miformacionctma.data.local

import com.sofia.miformacionctma.data.EstadoEvidencia
import com.sofia.miformacionctma.data.Evidencia

fun EvidenciaEntity.toDomain(): Evidencia =
    Evidencia(
        id = id,
        actividadId = actividadId,
        uri = uri,
        mimeType = mimeType,
        tamanoBytes = tamanoBytes,
        nombreArchivo = nombreArchivo,
        estado = estado.toEstadoEvidencia(),
        archivoPropio = archivoPropio
    )

fun Evidencia.toEntity(): EvidenciaEntity =
    EvidenciaEntity(
        id = id,
        actividadId = actividadId,
        uri = uri,
        mimeType = mimeType,
        tamanoBytes = tamanoBytes,
        nombreArchivo = nombreArchivo,
        estado = estado.name,
        archivoPropio = archivoPropio
    )

private fun String.toEstadoEvidencia(): EstadoEvidencia =
    runCatching {
        EstadoEvidencia.valueOf(this)
    }.getOrDefault(
        EstadoEvidencia.LOCAL
    )