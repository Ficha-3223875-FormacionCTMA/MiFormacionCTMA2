package com.sofia.miformacionctma.data.container

import android.content.Context
import com.sofia.miformacionctma.data.ActividadRepository
import com.sofia.miformacionctma.data.CategoriaRepository
import com.sofia.miformacionctma.data.RoomReporteRepository
import com.sofia.miformacionctma.data.local.DatabaseProvider
import com.sofia.miformacionctma.data.preferences.PreferenciasRepository

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

    val actividadRepository =
        ActividadRepository(
            dao = database.actividadDao()
        )

    val preferenciasRepository =
        PreferenciasRepository(
            context.applicationContext
        )
}