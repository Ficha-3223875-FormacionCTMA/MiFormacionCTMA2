package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.ui.components.TarjetaActividad
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme

@Composable
fun PantallaActividades(
    actividades: List<ActividadFormativa>,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

        if (actividades.isEmpty()) {

            EstadoVacio(
                modifier = Modifier.padding(innerPadding)
            )

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                Text(
                    text = "Mi Formación CTMA",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
                )

                Text(
                    text = "Actividades formativas",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = actividades,
                        key = { actividad ->
                            actividad.id
                        }
                    ) { actividad ->

                        TarjetaActividad(
                            actividad = actividad
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadoVacio(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "No hay actividades registradas",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Cuando existan actividades aparecerán en esta pantalla.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaActividadesPreview() {

    val actividadesEjemplo = listOf(
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
            descripcion = "Practicar variables, condiciones y funciones",
            progreso = 80,
            diasRestantes = 1,
            prioridad = Prioridad.ALTA
        ),

        ActividadFormativa(
            id = 3L,
            titulo = "Actividad con un título bastante largo para comprobar que la tarjeta se adapte correctamente",
            descripcion = "Esta actividad sirve para probar títulos largos.",
            progreso = 50,
            diasRestantes = 3,
            prioridad = Prioridad.MEDIA
        )
    )

    MiFormacionCTMATheme {
        PantallaActividades(
            actividades = actividadesEjemplo
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaActividadesVaciaPreview() {

    MiFormacionCTMATheme {
        PantallaActividades(
            actividades = emptyList()
        )
    }
}