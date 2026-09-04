package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.domain.promedioProgreso
import com.sofia.miformacionctma.ui.components.TarjetaActividad
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme

@Composable
fun PantallaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit = {},
    onEmptyAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "Mi Formación CTMA",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Actividades formativas",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text =
                    "${actividades.size} actividades · " +
                            "Promedio: ${"%.1f".format(
                                promedioProgreso(actividades)
                            )}%",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics {

                    contentDescription =
                        "Resumen: ${actividades.size} actividades. " +
                                "Promedio de progreso " +
                                "${"%.1f".format(
                                    promedioProgreso(actividades)
                                )} por ciento."
                }
            )
        }

        if (actividades.isEmpty()) {

            EstadoVacio(
                onEmptyAction = onEmptyAction
            )

        } else {

            ContenidoAdaptable(
                actividades = actividades,
                onActividadClick = onActividadClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun EstadoVacio(
    onEmptyAction: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "No hay actividades registradas.",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Puedes agregar una actividad para comenzar."
            )

            Button(
                onClick = onEmptyAction
            ) {

                Text(
                    text = "Agregar actividad"
                )
            }
        }
    }
}

@Composable
private fun ContenidoAdaptable(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit,
    modifier: Modifier = Modifier
) {

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {

        if (maxWidth < 600.dp) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = actividades,
                    key = { it.id }
                ) { actividad ->

                    TarjetaActividad(
                        actividad = actividad,
                        onActividadClick = onActividadClick
                    )
                }
            }

        } else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = actividades,
                    key = { it.id }
                ) { actividad ->

                    TarjetaActividad(
                        actividad = actividad,
                        onActividadClick = onActividadClick
                    )
                }
            }
        }
    }
}

private fun datosEjemploSemana3(): List<ActividadFormativa> = listOf(

    ActividadFormativa(
        1L,
        "Configurar Android Studio",
        "Preparar el entorno de desarrollo",
        100,
        -2,
        Prioridad.ALTA
    ),

    ActividadFormativa(
        2L,
        "Kotlin básico",
        "Practicar variables y condiciones",
        80,
        1,
        Prioridad.ALTA
    ),

    ActividadFormativa(
        3L,
        "Null safety",
        "Aplicar seguridad frente a valores nulos",
        40,
        2,
        Prioridad.MEDIA
    ),

    ActividadFormativa(
        4L,
        "Entregar evidencia",
        "Subir las capturas del proyecto",
        20,
        -1,
        Prioridad.ALTA
    ),

    ActividadFormativa(
        5L,
        "Repasar colecciones",
        "Practicar listas y operaciones sobre colecciones",
        0,
        5,
        Prioridad.BAJA
    ),

    ActividadFormativa(
        6L,
        "Funciones en Kotlin",
        "Practicar funciones y parámetros",
        65,
        3,
        Prioridad.MEDIA
    ),

    ActividadFormativa(
        7L,
        "Data classes",
        "Crear modelos para la aplicación",
        30,
        4,
        Prioridad.MEDIA
    ),

    ActividadFormativa(
        8L,
        "Colecciones Kotlin",
        "Trabajar con listas y filtros",
        75,
        6,
        Prioridad.BAJA
    ),

    ActividadFormativa(
        9L,
        "Diseño de interfaz",
        "Preparar la interfaz de Mi Formación CTMA",
        50,
        7,
        Prioridad.MEDIA
    ),

    ActividadFormativa(
        10L,
        "Evidencia Semana 3",
        "Preparar capturas de la aplicación",
        10,
        1,
        Prioridad.ALTA
    )
)

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PantallaActividadesPreview() {

    MiFormacionCTMATheme {

        PantallaActividades(
            actividades = datosEjemploSemana3()
        )
    }
}

@Preview(
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun PantallaActividadesFuenteGrandePreview() {

    MiFormacionCTMATheme {

        PantallaActividades(
            actividades = datosEjemploSemana3()
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 700,
    heightDp = 900
)
@Composable
private fun PantallaActividadesAnchaPreview() {

    MiFormacionCTMATheme {

        PantallaActividades(
            actividades = datosEjemploSemana3()
        )
    }
}