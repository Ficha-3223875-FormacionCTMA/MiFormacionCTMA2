package com.sofia.miformacionctma.data.remote.mapper

import com.sofia.miformacionctma.data.local.entity.ActividadEntity
import com.sofia.miformacionctma.data.remote.dto.ActividadDto
import com.sofia.miformacionctma.domain.Prioridad

fun ActividadDto.toEntity(): ActividadEntity {
    require(titulo.isNotBlank()) { "Título remoto vacío" }
    require(progreso in 0..100) { "Progreso remoto fuera de rango" }
    val prioridadValida = Prioridad.valueOf(prioridad.uppercase())
    return ActividadEntity(
        id = id,
        titulo = titulo.trim(),
        descripcion = descripcion?.trim()?.ifBlank { null },
        progreso = progreso,
        diasRestantes = diasRestantes,
        prioridad = prioridadValida.name,
        categoriaId = null,
        resuelto = progreso == 100
    )
}
