package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.*

@Composable
fun DetalleActividadScreen(
    actividad: ActividadFormativa?,
    onBack: () -> Unit,
    onEliminar: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        if (actividad == null) {

            Text(
                "Actividad no encontrada",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                "El identificador solicitado no corresponde a una actividad disponible."
            )

        } else {

            Text(
                actividad.titulo,
                style = MaterialTheme.typography.headlineSmall
            )

            actividad.descripcion
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    Text(it)
                }

            Text(
                "Fecha: ${
                    actividad.fecha.ifBlank { "Sin fecha" }
                }"
            )

            Text(
                "Estado: ${estadoActividad(actividad)}"
            )

            Text(
                "Progreso: ${actividad.progreso}%"
            )

            Text(
                "Prioridad: ${actividad.prioridad.name}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    onEliminar(actividad.id)
                }
            ) {
                Text("Eliminar actividad")
            }
        }

        OutlinedButton(
            onClick = onBack
        ) {
            Text("Volver")
        }
    }
}