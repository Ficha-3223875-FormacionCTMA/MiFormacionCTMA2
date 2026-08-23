package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.ui.screens.PantallaActividades
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val actividades = crearActividadesSemana3()

        setContent {
            MiFormacionCTMATheme {
                PantallaActividades(
                    actividades = actividades
                )
            }
        }
    }
}

private fun crearActividadesSemana3(): List<ActividadFormativa> {

    return listOf(

        ActividadFormativa(
            id = 1L,
            titulo = "Configurar Android Studio",
            descripcion = "Preparar correctamente el entorno de desarrollo.",
            progreso = 100,
            diasRestantes = -2,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 2L,
            titulo = "Repasar fundamentos de Kotlin",
            descripcion = "Practicar variables, funciones, condiciones y colecciones.",
            progreso = 80,
            diasRestantes = 1,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 3L,
            titulo = "Practicar Null Safety",
            descripcion = "Aplicar operadores seguros para evitar errores con valores nulos.",
            progreso = 60,
            diasRestantes = 2,
            prioridad = Prioridad.MEDIA
        ),

        ActividadFormativa(
            id = 4L,
            titulo = "Crear modelo ActividadFormativa",
            descripcion = "Representar correctamente los datos del dominio.",
            progreso = 100,
            diasRestantes = 0,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 5L,
            titulo = "Implementar reglas de negocio",
            descripcion = "Crear funciones de validación, estado, búsqueda y promedio.",
            progreso = 70,
            diasRestantes = 3,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 6L,
            titulo = "Realizar pruebas unitarias",
            descripcion = "Comprobar casos positivos, negativos y estados de actividad.",
            progreso = 100,
            diasRestantes = 0,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 7L,
            titulo = "Documentar Scrum",
            descripcion = "Registrar roles, artefactos y ceremonias de Scrum.",
            progreso = 100,
            diasRestantes = 1,
            prioridad = Prioridad.MEDIA
        ),

        ActividadFormativa(
            id = 8L,
            titulo = "Crear componente TarjetaActividad",
            descripcion = "Diseñar un componente Compose reutilizable.",
            progreso = 90,
            diasRestantes = 2,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 9L,
            titulo = "Construir PantallaActividades",
            descripcion = "Mostrar las actividades mediante LazyColumn.",
            progreso = 70,
            diasRestantes = 3,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 10L,
            titulo = "Actividad con un título muy largo para comprobar que la interfaz se adapta correctamente sin cortar el contenido",
            descripcion = "Prueba visual solicitada para validar títulos largos.",
            progreso = 0,
            diasRestantes = 5,
            prioridad = Prioridad.BAJA
        )
    )
}