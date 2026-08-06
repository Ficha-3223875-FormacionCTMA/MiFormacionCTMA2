package com.sofia.miformacionctma.domain

data class ActividadFormativa(
    val id: Long,
    val titulo: String,
    val descripcion: String?,
    val progreso: Int,
    val diasRestantes: Int,
    val prioridad: Prioridad
)
fun generarResumen(
    actividades: List<ActividadFormativa>
): String {

    val total = actividades.size

    val promedio = if (total > 0) {
        actividades.map { it.progreso }.average()
    } else {
        0.0
    }

    val completadas = actividades.count { it.progreso == 100 }

    val vencidas = actividades.count { it.diasRestantes < 0 }

    val urgentes = actividades.count { it.diasRestantes <= 2 }

    return """
        Total de actividades: $total
        Promedio: ${"%.1f".format(promedio)} %
        Completadas: $completadas
        Vencidas: $vencidas
        Urgentes: $urgentes
    """.trimIndent()
}