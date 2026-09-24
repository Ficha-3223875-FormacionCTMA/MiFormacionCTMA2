package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.data.local.ActividadEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad

fun ActividadDto.toDomain(): ActividadFormativa {
    return ActividadFormativa(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = prioridad.toPrioridad(),
        fecha = fecha
    )
}

fun ActividadDto.toEntity(): ActividadEntity {
    return ActividadEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = prioridad,
        fecha = fecha
    )
}

private fun String.toPrioridad(): Prioridad {
    return when (uppercase()) {
        "ALTA" -> Prioridad.ALTA
        "MEDIA" -> Prioridad.MEDIA
        "BAJA" -> Prioridad.BAJA
        else -> Prioridad.MEDIA
    }
}