package com.sofia.miformacionctma.domain

fun validarActividad(titulo: String, progreso: Int): List<String> {
    val errores = mutableListOf<String>()

    if (titulo.isBlank()) {
        errores.add("El título es obligatorio")
    }

    if (titulo.trim().length !in 3..80) {
        errores.add("El título debe tener entre 3 y 80 caracteres")
    }

    if (progreso !in 0..100) {
        errores.add("El progreso debe estar entre 0 y 100")
    }

    return errores
}

fun estadoActividad(progreso: Int, diasRestantes: Int): String = when {
    progreso == 100 -> "COMPLETADA"
    diasRestantes < 0 -> "VENCIDA"
    diasRestantes <= 2 -> "URGENTE"
    progreso > 0 -> "EN CURSO"
    else -> "PENDIENTE"
}

fun estadoActividad(actividad: ActividadFormativa): String =
    estadoActividad(actividad.progreso, actividad.diasRestantes)

fun actividadesUrgentes(
    actividades: List<ActividadFormativa>
): List<ActividadFormativa> =
    actividades.filter {
        it.progreso < 100 && it.diasRestantes <= 2
    }

fun promedioProgreso(
    actividades: List<ActividadFormativa>
): Double =
    if (actividades.isEmpty()) {
        0.0
    } else {
        actividades.map { it.progreso }.average()
    }

fun buscarPorTitulo(
    actividades: List<ActividadFormativa>,
    texto: String
): List<ActividadFormativa> {
    val criterio = texto.trim().lowercase()

    return actividades.filter {
        it.titulo.lowercase().contains(criterio)
    }
}

fun ordenarActividades(
    actividades: List<ActividadFormativa>
): List<ActividadFormativa> =
    actividades.sortedWith(
        compareBy<ActividadFormativa>(
            { it.diasRestantes },
            { it.progreso }
        )
    )

// Función agregada para la prueba TDD de Semana 8.
// Por ahora devuelve false porque estamos en la fase RED.
fun progresoEsValidoParaCompletar(progreso: Int): Boolean =
    progreso == 100