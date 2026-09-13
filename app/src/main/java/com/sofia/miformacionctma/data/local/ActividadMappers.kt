package com.sofia.miformacionctma.data.local

import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad

fun ActividadEntity.toDomain(): ActividadFormativa =
    ActividadFormativa(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = runCatching {
            Prioridad.valueOf(prioridad)
        }.getOrDefault(Prioridad.MEDIA),
        fecha = fecha
    )

fun ActividadFormativa.toEntity(): ActividadEntity =
    ActividadEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = prioridad.name,
        fecha = fecha
    )
