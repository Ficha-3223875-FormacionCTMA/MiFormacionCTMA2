package com.sofia.miformacionctma.data.local

import androidx.room3.Embedded
import androidx.room3.Relation
import com.sofia.miformacionctma.domain.Categoria
import com.sofia.miformacionctma.domain.Reporte

fun ReporteEntity.toDomain(): Reporte = Reporte(
    id = id,
    titulo = titulo,
    categoriaId = categoriaId,
    resuelto = resuelto
)

fun Reporte.toEntity(): ReporteEntity = ReporteEntity(
    id = id,
    titulo = titulo,
    categoriaId = categoriaId,
    resuelto = resuelto
)

fun CategoriaEntity.toDomain(): Categoria =
    Categoria(
        id = id,
        nombre = nombre
    )

fun Categoria.toEntity(): CategoriaEntity =
    CategoriaEntity(
        id = id,
        nombre = nombre
    )

data class ReporteConCategoria(
    @Embedded val reporte: ReporteEntity,

    @Relation(
        entity = CategoriaEntity::class,
        parentColumns = ["categoriaId"],
        entityColumns = ["id"]
    )
    val categoria: CategoriaEntity?
)

fun ReporteConCategoria.toDomain(): Pair<Reporte, Categoria?> =
    reporte.toDomain() to categoria?.toDomain()