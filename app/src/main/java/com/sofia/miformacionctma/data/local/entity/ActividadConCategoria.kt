package com.sofia.miformacionctma.data.local.entity

import androidx.room3.Embedded
import androidx.room3.Relation

data class ActividadConCategoria(
    @Embedded
    val actividad: ActividadEntity,

    @Relation(
        parentColumns = ["categoriaId"],
        entityColumns = ["id"]
    )
    val categoria: CategoriaEntity?
)