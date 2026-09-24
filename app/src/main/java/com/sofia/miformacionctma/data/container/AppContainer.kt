package com.sofia.miformacionctma.data.container

import android.content.Context
import com.sofia.miformacionctma.data.ActividadRepository
import com.sofia.miformacionctma.data.ActividadRepositoryImpl
import com.sofia.miformacionctma.data.CategoriaRepository
import com.sofia.miformacionctma.data.EvidenciaRepository
import com.sofia.miformacionctma.data.EvidenciaRepositoryImpl
import com.sofia.miformacionctma.data.RoomReporteRepository
import com.sofia.miformacionctma.data.local.DatabaseProvider
import com.sofia.miformacionctma.data.preferences.PreferenciasRepository
import com.sofia.miformacionctma.data.preferences.PreferenciasSource
import com.sofia.miformacionctma.data.remote.RemoteActividadDataSource
import com.sofia.miformacionctma.data.remote.RetrofitProvider

class AppContainer(context: Context) {

    private val database =
        DatabaseProvider.get(context)

    private val categoriaRepository =
        CategoriaRepository(
            database.categoriaDao()
        )

    val reporteRepository =
        RoomReporteRepository(
            reporteDao = database.reporteDao(),
            categoriaRepository = categoriaRepository
        )

    // Semana 8 - API remota
    private val remoteActividadDataSource =
        RemoteActividadDataSource(
            RetrofitProvider.actividadApi
        )

    // Semana 8 - Room + API
    val actividadRepository: ActividadRepository =
        ActividadRepositoryImpl(
            dao = database.actividadDao(),
            remote = remoteActividadDataSource
        )

    // Semana 9 - Evidencias
    val evidenciaRepository: EvidenciaRepository =
        EvidenciaRepositoryImpl(
            dao = database.evidenciaDao(),
            contentResolver =
                context.applicationContext.contentResolver
        )

    val preferenciasRepository: PreferenciasSource =
        PreferenciasRepository(
            context.applicationContext
        )
}