package com.sofia.miformacionctma.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "preferencias_usuario"
)

class PreferencesRepository(
    private val context: Context
) {

    companion object {
        private val ORDEN_KEY: Preferences.Key<String> =
            stringPreferencesKey("orden_actividades")
    }

    val ordenActividades: Flow<String> =
        context.dataStore.data.map { preferencias ->
            preferencias[ORDEN_KEY] ?: "FECHA"
        }

    suspend fun guardarOrden(orden: String) {
        context.dataStore.edit { preferencias ->
            preferencias[ORDEN_KEY] = orden
        }
    }
}