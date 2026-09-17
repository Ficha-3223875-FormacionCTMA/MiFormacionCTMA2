package com.sofia.miformacionctma.data.local.entity

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "categorias",
    indices = [
        Index(value = ["nombre"], unique = true)
    ]
)
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String
)