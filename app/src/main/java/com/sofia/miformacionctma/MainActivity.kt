package com.sofia.miformacionctma

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    PantallaActividades(
                        actividades = actividades,
                        modifier = Modifier.padding(innerPadding),
                        onActividadClick = { actividad ->

                            Toast.makeText(
                                this,
                                "Seleccionaste: ${actividad.titulo}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

private fun crearActividadesSemana3(): List<ActividadFormativa> {

    return listOf(

        ActividadFormativa(
            id = 1L,
            titulo = "Configurar Android Studio",
            descripcion = "Preparar el entorno de desarrollo",
            progreso = 100,
            diasRestantes = -2,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 2L,
            titulo = "Kotlin básico",
            descripcion = "Practicar variables y condiciones",
            progreso = 80,
            diasRestantes = 1,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 3L,
            titulo = "Null safety",
            descripcion = "Aplicar seguridad frente a valores nulos",
            progreso = 40,
            diasRestantes = 2,
            prioridad = Prioridad.MEDIA
        ),

        ActividadFormativa(
            id = 4L,
            titulo = "Entregar evidencia",
            descripcion = "Subir las capturas del proyecto",
            progreso = 20,
            diasRestantes = -1,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 5L,
            titulo = "Repasar colecciones",
            descripcion = "Practicar listas y operaciones sobre colecciones",
            progreso = 0,
            diasRestantes = 5,
            prioridad = Prioridad.BAJA
        ),

        ActividadFormativa(
            id = 6L,
            titulo = "Funciones en Kotlin",
            descripcion = "Practicar funciones y parámetros",
            progreso = 65,
            diasRestantes = 3,
            prioridad = Prioridad.MEDIA
        ),

        ActividadFormativa(
            id = 7L,
            titulo = "Data classes",
            descripcion = "Crear modelos para la aplicación",
            progreso = 30,
            diasRestantes = 4,
            prioridad = Prioridad.MEDIA
        ),

        ActividadFormativa(
            id = 8L,
            titulo = "Colecciones Kotlin",
            descripcion = "Trabajar con listas y filtros",
            progreso = 75,
            diasRestantes = 6,
            prioridad = Prioridad.BAJA
        ),

        ActividadFormativa(
            id = 9L,
            titulo = "Diseño de interfaz",
            descripcion = "Preparar la interfaz de Mi Formación CTMA",
            progreso = 50,
            diasRestantes = 7,
            prioridad = Prioridad.MEDIA
        ),

        ActividadFormativa(
            id = 10L,
            titulo = "Evidencia Semana 3",
            descripcion = "Preparar capturas de la aplicación",
            progreso = 10,
            diasRestantes = 1,
            prioridad = Prioridad.ALTA
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun MainActivityPreview() {

    MiFormacionCTMATheme {

        PantallaActividades(
            actividades = crearActividadesSemana3()
        )
    }
}