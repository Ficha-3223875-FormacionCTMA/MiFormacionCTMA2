package com.sofia.miformacionctma.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "preferencias"
)

data class PreferenciasUi(
    val categoriaId: String? = null,
    val orden: String = "TITULO_ASC",
    val modoVisualizacion: String = "LISTA",
    val filtrosActivos: Boolean = false,
    val recordatoriosActivos: Boolean = false,
    val permisoNotificacionesSolicitado: Boolean = false
)

interface PreferenciasSource {

    val preferencias: Flow<PreferenciasUi>

    suspend fun guardarCategoria(
        categoriaId: String?
    )

    suspend fun guardarOrden(
        orden: String
    )

    suspend fun guardarModoVisualizacion(
        modo: String
    )

    suspend fun guardarFiltrosActivos(
        activos: Boolean
    )

    suspend fun guardarRecordatoriosActivos(
        activos: Boolean
    )

    suspend fun guardarPermisoNotificacionesSolicitado(
        solicitado: Boolean
    )
}

class PreferenciasRepository(
    private val context: Context
) : PreferenciasSource {

    private object Keys {

        val CATEGORIA_FILTRO =
            stringPreferencesKey(
                "categoria_filtro"
            )

        val ORDEN =
            stringPreferencesKey(
                "orden"
            )

        val MODO_VISUALIZACION =
            stringPreferencesKey(
                "modo_visualizacion"
            )

        val FILTROS_ACTIVOS =
            booleanPreferencesKey(
                "filtros_activos"
            )

        val RECORDATORIOS_ACTIVOS =
            booleanPreferencesKey(
                "recordatorios_activos"
            )

        val PERMISO_NOTIFICACIONES_SOLICITADO =
            booleanPreferencesKey(
                "permiso_notificaciones_solicitado"
            )
    }

    override val preferencias: Flow<PreferenciasUi> =
        context.dataStore.data.map { preferences ->

            PreferenciasUi(
                categoriaId =
                    preferences[
                        Keys.CATEGORIA_FILTRO
                    ],

                orden =
                    preferences[
                        Keys.ORDEN
                    ] ?: "TITULO_ASC",

                modoVisualizacion =
                    preferences[
                        Keys.MODO_VISUALIZACION
                    ] ?: "LISTA",

                filtrosActivos =
                    preferences[
                        Keys.FILTROS_ACTIVOS
                    ] ?: false,

                recordatoriosActivos =
                    preferences[
                        Keys.RECORDATORIOS_ACTIVOS
                    ] ?: false,

                permisoNotificacionesSolicitado =
                    preferences[
                        Keys.PERMISO_NOTIFICACIONES_SOLICITADO
                    ] ?: false
            )
        }

    override suspend fun guardarCategoria(
        categoriaId: String?
    ) {

        context.dataStore.edit { preferences ->

            if (categoriaId == null) {

                preferences.remove(
                    Keys.CATEGORIA_FILTRO
                )

            } else {

                preferences[
                    Keys.CATEGORIA_FILTRO
                ] = categoriaId
            }
        }
    }

    override suspend fun guardarOrden(
        orden: String
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                Keys.ORDEN
            ] = orden
        }
    }

    override suspend fun guardarModoVisualizacion(
        modo: String
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                Keys.MODO_VISUALIZACION
            ] = modo
        }
    }

    override suspend fun guardarFiltrosActivos(
        activos: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                Keys.FILTROS_ACTIVOS
            ] = activos
        }
    }

    override suspend fun guardarRecordatoriosActivos(
        activos: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                Keys.RECORDATORIOS_ACTIVOS
            ] = activos
        }
    }

    override suspend fun guardarPermisoNotificacionesSolicitado(
        solicitado: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                Keys.PERMISO_NOTIFICACIONES_SOLICITADO
            ] = solicitado
        }
    }
}