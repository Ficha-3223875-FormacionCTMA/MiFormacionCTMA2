package com.sofia.miformacionctma.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "categorias",
    indices = [Index(value = ["nombre"], unique = true)]
)
data class CategoriaEntity(
    @PrimaryKey val id: String,
    val nombre: String
)

@Entity(
    tableName = "reportes",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["titulo"]),
        Index(value = ["categoriaId"])
    ]
)
data class ReporteEntity(
    @PrimaryKey val id: String,
    val titulo: String,
    @ColumnInfo(name = "categoriaId") val categoriaId: String? = null,
    val resuelto: Boolean = false
)
