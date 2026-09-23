package com.sofia.miformacionctma.data.preferences

import kotlinx.coroutines.flow.Flow

data class PreferenciasUi(
    val orden: String = "FECHA",
    val filtroPrioridad: String = "TODAS",
    val modoVisualizacion: String = "DETALLADO"
)

interface PreferenciasDataSource {
    val preferencias: Flow<PreferenciasUi>
    suspend fun guardarOrden(orden: String)
    suspend fun guardarFiltroPrioridad(filtro: String)
    suspend fun guardarModoVisualizacion(modo: String)
}
