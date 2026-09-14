package com.sofia.miformacionctma.data.local

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "actividades",
    indices = [
        Index(value = ["titulo"]),
        Index(value = ["fecha"])
    ]
)
data class ActividadEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val titulo: String,

    val descripcion: String?,

    val progreso: Int,

    val diasRestantes: Int,

    val prioridad: String,

    val fecha: String
)