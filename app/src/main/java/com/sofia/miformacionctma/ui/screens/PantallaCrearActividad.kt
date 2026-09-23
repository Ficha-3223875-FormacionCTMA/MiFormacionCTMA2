package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad

@Composable
fun PantallaCrearActividad(
    actividadEditar: ActividadFormativa? = null,
    onGuardar: (ActividadFormativa) -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {

    var titulo by remember(actividadEditar) {
        mutableStateOf(actividadEditar?.titulo ?: "")
    }

    var descripcion by remember(actividadEditar) {
        mutableStateOf(actividadEditar?.descripcion ?: "")
    }

    var progresoTexto by remember(actividadEditar) {
        mutableStateOf(actividadEditar?.progreso?.toString() ?: "0")
    }

    var diasRestantesTexto by remember(actividadEditar) {
        mutableStateOf(actividadEditar?.diasRestantes?.toString() ?: "0")
    }

    // Para actividades nuevas se inicia en MEDIA.
    // Al editar, conserva la prioridad que ya tenía la actividad.
    var prioridadSeleccionada by remember(actividadEditar) {
        mutableStateOf(actividadEditar?.prioridad ?: Prioridad.MEDIA)
    }

    var mensajeError by remember(actividadEditar) {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = if (actividadEditar == null) {
                "Nueva actividad"
            } else {
                "Editar actividad"
            }
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = progresoTexto,
            onValueChange = { progresoTexto = it },
            label = { Text("Progreso (0 - 100)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = diasRestantesTexto,
            onValueChange = { diasRestantesTexto = it },
            label = { Text("Días restantes") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Prioridad")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FilterChip(
                selected = prioridadSeleccionada == Prioridad.BAJA,
                onClick = {
                    prioridadSeleccionada = Prioridad.BAJA
                },
                label = {
                    Text("Baja")
                }
            )

            FilterChip(
                selected = prioridadSeleccionada == Prioridad.MEDIA,
                onClick = {
                    prioridadSeleccionada = Prioridad.MEDIA
                },
                label = {
                    Text("Media")
                }
            )

            FilterChip(
                selected = prioridadSeleccionada == Prioridad.ALTA,
                onClick = {
                    prioridadSeleccionada = Prioridad.ALTA
                },
                label = {
                    Text("Alta")
                }
            )
        }

        mensajeError?.let {
            Text(text = it)
        }

        Button(
            onClick = {

                val progreso = progresoTexto.toIntOrNull()
                val diasRestantes = diasRestantesTexto.toIntOrNull()

                when {
                    titulo.isBlank() -> {
                        mensajeError = "El título es obligatorio"
                    }

                    progreso == null || progreso !in 0..100 -> {
                        mensajeError = "El progreso debe estar entre 0 y 100"
                    }

                    diasRestantes == null -> {
                        mensajeError = "Los días restantes deben ser un número"
                    }

                    else -> {
                        mensajeError = null

                        onGuardar(
                            ActividadFormativa(
                                id = actividadEditar?.id ?: 0L,
                                titulo = titulo.trim(),
                                descripcion = descripcion
                                    .trim()
                                    .ifBlank { null },
                                progreso = progreso,
                                diasRestantes = diasRestantes,
                                prioridad = prioridadSeleccionada
                            )
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (actividadEditar == null) {
                    "Guardar actividad"
                } else {
                    "Guardar cambios"
                }
            )
        }

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}