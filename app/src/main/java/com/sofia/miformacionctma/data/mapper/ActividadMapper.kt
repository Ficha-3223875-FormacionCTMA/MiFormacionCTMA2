package com.sofia.miformacionctma.data.mapper

import com.sofia.miformacionctma.data.local.entity.ActividadEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad

fun ActividadEntity.toDomain(): ActividadFormativa {
    return ActividadFormativa(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = Prioridad.valueOf(prioridad)
    )
}

fun ActividadFormativa.toEntity(): ActividadEntity {
    return ActividadEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = prioridad.name,
        categoriaId = null
    )
}