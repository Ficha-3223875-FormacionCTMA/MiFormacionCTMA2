package com.sofia.miformacionctma.domain

data class Reporte(
    val id: String,
    val titulo: String,
    val categoriaId: String? = null,
    val resuelto: Boolean = false
)
