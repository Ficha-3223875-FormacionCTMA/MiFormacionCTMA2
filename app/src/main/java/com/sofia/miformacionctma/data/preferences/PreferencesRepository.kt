package com.sofia.miformacionctma.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

class PreferencesRepository(
    private val context: Context
) : PreferenciasDataSource {

    companion object {
        private val ORDEN_KEY = stringPreferencesKey("orden_actividades")
        private val FILTRO_PRIORIDAD_KEY = stringPreferencesKey("filtro_prioridad")
        private val MODO_VISUALIZACION_KEY = stringPreferencesKey("modo_visualizacion")
    }

    override val preferencias: Flow<PreferenciasUi> =
        context.dataStore.data.map { datos ->
            PreferenciasUi(
                orden = datos[ORDEN_KEY] ?: "FECHA",
                filtroPrioridad = datos[FILTRO_PRIORIDAD_KEY] ?: "TODAS",
                modoVisualizacion = datos[MODO_VISUALIZACION_KEY] ?: "DETALLADO"
            )
        }

    val ordenActividades: Flow<String> = preferencias.map { it.orden }

    override suspend fun guardarOrden(orden: String) {
        context.dataStore.edit { it[ORDEN_KEY] = orden }
    }

    override suspend fun guardarFiltroPrioridad(filtro: String) {
        context.dataStore.edit { it[FILTRO_PRIORIDAD_KEY] = filtro }
    }

    override suspend fun guardarModoVisualizacion(modo: String) {
        context.dataStore.edit { it[MODO_VISUALIZACION_KEY] = modo }
    }
}
