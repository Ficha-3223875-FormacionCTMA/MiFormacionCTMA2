package com.sofia.miformacionctma.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.estadoActividad

@Composable
fun TarjetaActividad(
    actividad: ActividadFormativa,
    onEditar: (ActividadFormativa) -> Unit,
    onEliminar: (ActividadFormativa) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = actividad.descripcion
                    ?.takeIf { it.isNotBlank() }
                    ?: "Sin descripción"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Progreso: ${actividad.progreso}%"
            )

            Text(
                text = "Días restantes: ${actividad.diasRestantes}"
            )

            Text(
                text = "Prioridad: ${actividad.prioridad}"
            )

            Text(
                text = "Estado: ${estadoActividad(actividad)}"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = {
                        onEditar(actividad)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar")
                }

                OutlinedButton(
                    onClick = {
                        onEliminar(actividad)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}