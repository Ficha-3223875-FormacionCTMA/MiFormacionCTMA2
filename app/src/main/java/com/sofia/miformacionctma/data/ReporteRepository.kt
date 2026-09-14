package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.domain.Categoria
import com.sofia.miformacionctma.domain.Reporte
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ReporteRepository {
    val reportes: StateFlow<List<Reporte>>
    fun agregar(reporte: Reporte)
    fun actualizar(reporte: Reporte) {}
    fun eliminar(id: String) {}
    suspend fun buscarPorId(id: String): Reporte? = reportes.value.firstOrNull { it.id == id }
    fun buscarPorTexto(texto: String): Flow<List<Reporte>> =
        kotlinx.coroutines.flow.flowOf(reportes.value.filter { it.titulo.contains(texto, ignoreCase = true) })
    fun buscarPorCategoria(categoriaId: String): Flow<List<Reporte>> =
        kotlinx.coroutines.flow.flowOf(reportes.value.filter { it.categoriaId == categoriaId })
    suspend fun buscarConCategoria(id: String): Pair<Reporte, Categoria?>? =
        buscarPorId(id)?.let { it to null }
}

/** Repositorio temporal conservado como referencia del incremento anterior. */
class InMemoryReporteRepository : ReporteRepository {
    private val _reportes = MutableStateFlow<List<Reporte>>(emptyList())
    override val reportes: StateFlow<List<Reporte>> = _reportes.asStateFlow()

    override fun agregar(reporte: Reporte) {
        _reportes.value = _reportes.value + reporte
    }

    override fun actualizar(reporte: Reporte) {
        _reportes.value = _reportes.value.map { if (it.id == reporte.id) reporte else it }
    }

    override fun eliminar(id: String) {
        _reportes.value = _reportes.value.filterNot { it.id == id }
    }
}
