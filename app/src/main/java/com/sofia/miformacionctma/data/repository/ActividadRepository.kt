package com.sofia.miformacionctma.data.repository

import com.sofia.miformacionctma.data.local.dao.ActividadDao
import com.sofia.miformacionctma.data.mapper.toDomain
import com.sofia.miformacionctma.data.mapper.toEntity
import com.sofia.miformacionctma.data.remote.RemoteActividadDataSource
import com.sofia.miformacionctma.data.remote.mapper.toEntity as remoteToEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val actividadDao: ActividadDao,
    private val remote: RemoteActividadDataSource? = null
) : ActividadDataSource {
    override fun observarTodas(): Flow<List<ActividadFormativa>> =
        actividadDao.observarTodas().map { lista -> lista.map { it.toDomain() } }

    override fun buscarPorTitulo(texto: String): Flow<List<ActividadFormativa>> =
        actividadDao.buscarPorTitulo(texto).map { lista -> lista.map { it.toDomain() } }

    override fun observarConFalloSimulado(): Flow<List<ActividadFormativa>> = flow {
        throw IllegalStateException("Fallo simulado para CA-05")
    }

    override fun observarPorId(id: Long): Flow<ActividadFormativa?> =
        actividadDao.observarPorId(id).map { it?.toDomain() }

    override suspend fun insertar(actividad: ActividadFormativa): Long = actividadDao.insertar(actividad.toEntity())
    override suspend fun actualizar(actividad: ActividadFormativa) = actividadDao.actualizar(actividad.toEntity())
    override suspend fun eliminar(actividad: ActividadFormativa) = actividadDao.eliminar(actividad.toEntity())
    override suspend fun eliminarTodas() = actividadDao.eliminarTodas()

    override suspend fun refresh(): Int {
        val remoto = requireNotNull(remote) { "Fuente remota no configurada" }
        val entidades = remoto.listar().map { it.remoteToEntity() }
        actividadDao.reemplazarTodas(entidades)
        return entidades.size
    }
}
