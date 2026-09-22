package com.sofia.miformacionctma.data.local

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "evidencias",
    foreignKeys = [
        ForeignKey(
            entity = ActividadEntity::class,
            parentColumns = ["id"],
            childColumns = ["actividadId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["actividadId"],
            unique = true
        )
    ]
)
data class EvidenciaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val actividadId: Long,

    val uri: String,

    val mimeType: String,

    val tamanoBytes: Long,

    val nombreArchivo: String,

    val estado: String,

    val archivoPropio: Boolean
)