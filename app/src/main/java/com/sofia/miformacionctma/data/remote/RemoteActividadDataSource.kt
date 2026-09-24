package com.sofia.miformacionctma.data.remote

import com.sofia.miformacionctma.data.remote.api.ActividadesApi
import com.sofia.miformacionctma.data.remote.dto.ActividadDto
import com.sofia.miformacionctma.domain.network.RemoteFailure
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteActividadDataSource(private val api: ActividadesApi) {
    suspend fun listar(): List<ActividadDto> = ejecutar { api.listar() }

    private suspend fun <T> ejecutar(llamada: suspend () -> retrofit2.Response<T>): T {
        try {
            val response = llamada()
            when {
                response.code() == 401 -> throw RemoteFailure.Sesion()
                response.code() == 404 -> throw RemoteFailure.NoEncontrado()
                response.code() >= 500 -> throw RemoteFailure.Servidor()
                !response.isSuccessful -> throw RemoteFailure.Otro()
            }
            return response.body() ?: throw RemoteFailure.DatosInvalidos()
        } catch (e: CancellationException) {
            throw e
        } catch (e: RemoteFailure) {
            throw e
        } catch (e: SocketTimeoutException) {
            throw RemoteFailure.Timeout()
        } catch (e: SerializationException) {
            throw RemoteFailure.DatosInvalidos()
        } catch (e: IOException) {
            throw RemoteFailure.SinConexion()
        } catch (_: IllegalArgumentException) {
            throw RemoteFailure.DatosInvalidos()
        }
    }
}
