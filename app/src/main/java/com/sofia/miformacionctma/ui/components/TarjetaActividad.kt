package com.sofia.miformacionctma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.estadoActividad

@Composable
fun TarjetaActividad(
    actividad: ActividadFormativa,
    onActividadClick: (ActividadFormativa) -> Unit = {}
) {

    val estado = estadoActividad(actividad)

    val progreso = actividad.progreso.coerceIn(0, 100)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onActividadClick(actividad)
            }
            .semantics {
                contentDescription =
                    "${actividad.titulo}. " +
                            "Estado: $estado. " +
                            "Progreso: $progreso por ciento."
            }
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            actividad.descripcion
                ?.takeIf { it.isNotBlank() }
                ?.let { descripcion ->

                    Text(
                        text = descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Estado: $estado",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "${actividad.diasRestantes} días",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            LinearProgressIndicator(
                progress = {
                    progreso / 100f
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Progreso: $progreso%",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Prioridad: ${actividad.prioridad}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}