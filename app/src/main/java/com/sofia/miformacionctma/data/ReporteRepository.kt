package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.domain.Reporte
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ReporteRepository {
    val reportes: StateFlow<List<Reporte>>
    fun agregar(reporte: Reporte)
}

class InMemoryReporteRepository : ReporteRepository {
    private val _reportes = MutableStateFlow<List<Reporte>>(emptyList())
    override val reportes: StateFlow<List<Reporte>> = _reportes.asStateFlow()

    override fun agregar(reporte: Reporte) {
        _reportes.value = _reportes.value + reporte
    }
}
