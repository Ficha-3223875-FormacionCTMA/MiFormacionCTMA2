package com.sofia.miformacionctma.data

import com.sofia.miformacionctma.data.local.ReporteDao
import com.sofia.miformacionctma.data.local.toDomain
import com.sofia.miformacionctma.data.local.toEntity
import com.sofia.miformacionctma.domain.Categoria
import com.sofia.miformacionctma.domain.Reporte
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoomReporteRepository(
    private val reporteDao: ReporteDao,
    private val categoriaRepository: CategoriaRepository
) : ReporteRepository {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    override val reportes: StateFlow<List<Reporte>> =
        reporteDao.observarTodos()
            .map { lista ->
                lista.map { it.toDomain() }
            }
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    override fun agregar(reporte: Reporte) {
        scope.launch {
            reporteDao.insertar(reporte.toEntity())
        }
    }

    override fun actualizar(reporte: Reporte) {
        scope.launch {
            reporteDao.actualizar(reporte.toEntity())
        }
    }

    override fun eliminar(id: String) {
        scope.launch {
            reporteDao.eliminarPorId(id)
        }
    }

    override suspend fun buscarPorId(id: String): Reporte? {
        return reporteDao.buscarPorId(id)?.toDomain()
    }

    override fun buscarPorTexto(texto: String): Flow<List<Reporte>> {
        return reporteDao.buscarPorTexto(texto)
            .map { lista ->
                lista.map { it.toDomain() }
            }
    }

    override fun buscarPorCategoria(categoriaId: String): Flow<List<Reporte>> {
        return reporteDao.buscarPorCategoria(categoriaId)
            .map { lista ->
                lista.map { it.toDomain() }
            }
    }

    override suspend fun buscarConCategoria(
        id: String
    ): Pair<Reporte, Categoria?>? {
        return reporteDao.buscarConCategoria(id)?.toDomain()
    }
}

class CategoriaRepository(
    private val dao: com.sofia.miformacionctma.data.local.CategoriaDao
) {

    val categorias: Flow<List<Categoria>> =
        dao.observarTodas()
            .map { lista ->
                lista.map { it.toDomain() }
            }

    suspend fun agregar(categoria: Categoria) {
        dao.insertar(categoria.toEntity())
    }

    suspend fun actualizar(categoria: Categoria) {
        dao.actualizar(categoria.toEntity())
    }

    suspend fun eliminar(categoria: Categoria) {
        dao.eliminar(categoria.toEntity())
    }

    suspend fun buscarPorId(id: String): Categoria? {
        return dao.buscarPorId(id)?.toDomain()
    }
}