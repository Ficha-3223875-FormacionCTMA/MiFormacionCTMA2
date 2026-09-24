package com.sofia.miformacionctma.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActividadDto(
    val id: Long,
    val titulo: String,
    val descripcion: String? = null,
    val progreso: Int,
    @SerialName("dias_restantes") val diasRestantes: Int,
    val prioridad: String
)
