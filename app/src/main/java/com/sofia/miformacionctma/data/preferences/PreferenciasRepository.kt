package com.sofia.miformacionctma.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.preferenciasDataStore by preferencesDataStore(name = "preferencias_reportactma")

data class PreferenciasUi(
    val categoriaId: String? = null,
    val orden: String = "TITULO_ASC",
    val modoVisualizacion: String = "LISTA"
)

class PreferenciasRepository(private val context: Context) {
    private object Keys {
        val categoria = stringPreferencesKey("categoria_filtro")
        val orden = stringPreferencesKey("orden")
        val modo = stringPreferencesKey("modo_visualizacion")
        val filtrosActivos = booleanPreferencesKey("filtros_activos")
    }

    val preferencias: Flow<PreferenciasUi> = context.preferenciasDataStore.data.map { prefs ->
        PreferenciasUi(
            categoriaId = prefs[Keys.categoria],
            orden = prefs[Keys.orden] ?: "TITULO_ASC",
            modoVisualizacion = prefs[Keys.modo] ?: "LISTA"
        )
    }

    suspend fun guardarCategoria(categoriaId: String?) {
        context.preferenciasDataStore.edit { prefs ->
            if (categoriaId == null) prefs.remove(Keys.categoria) else prefs[Keys.categoria] = categoriaId
        }
    }

    suspend fun guardarOrden(orden: String) {
        context.preferenciasDataStore.edit { it[Keys.orden] = orden }
    }

    suspend fun guardarModoVisualizacion(modo: String) {
        context.preferenciasDataStore.edit { it[Keys.modo] = modo }
    }

    suspend fun guardarFiltrosActivos(activos: Boolean) {
        context.preferenciasDataStore.edit { it[Keys.filtrosActivos] = activos }
    }
}
