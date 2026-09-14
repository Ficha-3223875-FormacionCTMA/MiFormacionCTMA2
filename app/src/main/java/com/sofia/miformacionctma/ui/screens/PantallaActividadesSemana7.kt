package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.ui.components.TarjetaActividad
import com.sofia.miformacionctma.ui.state.ListadoUiState
import com.sofia.miformacionctma.ui.state.OperacionUiState

@Composable
fun PantallaActividadesSemana7(
    uiState: ListadoUiState,
    operacionUiState: OperacionUiState,
    textoBusqueda: String,
    orden: String,
    modoVisualizacion: String,
    onBusquedaChange: (String) -> Unit,
    onOrdenChange: (String) -> Unit,
    onModoChange: (String) -> Unit,
    onActividadClick: (ActividadFormativa) -> Unit,
    onNuevaActividad: () -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Mi Formación CTMA",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Semana 7 · Estado reactivo",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = onBusquedaChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar actividad") },
                singleLine = true
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = orden == "TITULO_ASC",
                    onClick = { onOrdenChange("TITULO_ASC") },
                    label = { Text("A-Z") }
                )
                FilterChip(
                    selected = orden == "PROGRESO_DESC",
                    onClick = { onOrdenChange("PROGRESO_DESC") },
                    label = { Text("Progreso") }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = modoVisualizacion == "LISTA",
                    onClick = { onModoChange("LISTA") },
                    label = { Text("Lista") }
                )
                FilterChip(
                    selected = modoVisualizacion == "CUADRICULA",
                    onClick = { onModoChange("CUADRICULA") },
                    label = { Text("Cuadrícula") }
                )
            }

            Button(
                onClick = onNuevaActividad,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nueva actividad")
            }

            when (val operacion = operacionUiState) {
                OperacionUiState.Inactiva -> Unit
                OperacionUiState.EnCurso -> {
                    Text(
                        "Guardando cambios…",
                    )
                }
                OperacionUiState.Exitosa -> {
                    Text("Operación realizada correctamente.")
                }
                is OperacionUiState.Fallida -> {
                    Text(
                        "Error: ${operacion.mensaje}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (val estado = uiState) {
                ListadoUiState.Cargando -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator()
                        Text("Cargando actividades…")
                    }
                }

                ListadoUiState.Vacio -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "No hay actividades para mostrar.",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Button(onClick = onNuevaActividad) {
                            Text("Agregar actividad")
                        }
                    }
                }

                is ListadoUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "No fue posible cargar las actividades.",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(estado.mensaje)
                        OutlinedButton(onClick = onReintentar) {
                            Text("Reintentar")
                        }
                    }
                }

                is ListadoUiState.Contenido -> {
                    if (modoVisualizacion == "CUADRICULA") {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = estado.actividades,
                                key = { it.id }
                            ) { actividad ->
                                TarjetaActividad(
                                    actividad = actividad,
                                    onActividadClick = onActividadClick
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = estado.actividades,
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
        }
    }
}

