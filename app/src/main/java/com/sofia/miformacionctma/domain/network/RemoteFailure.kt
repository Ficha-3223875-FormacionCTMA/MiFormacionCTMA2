package com.sofia.miformacionctma.domain.network

sealed class RemoteFailure(message: String) : Exception(message) {
    class SinConexion : RemoteFailure("Sin conexión. Revisa la red y vuelve a intentar.")
    class Timeout : RemoteFailure("La actualización tardó demasiado. Conservamos los datos locales.")
    class Sesion : RemoteFailure("La sesión debe renovarse para continuar.")
    class NoEncontrado : RemoteFailure("El recurso solicitado ya no está disponible.")
    class Servidor : RemoteFailure("El servicio no está disponible temporalmente.")
    class DatosInvalidos : RemoteFailure("El servidor devolvió datos que no se pudieron validar.")
    class Otro : RemoteFailure("No fue posible actualizar en este momento.")
}
