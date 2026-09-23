package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.ui.components.TarjetaActividad
import com.sofia.miformacionctma.ui.viewmodel.ListadoUiState
import com.sofia.miformacionctma.ui.viewmodel.OperacionUiState

@Composable
fun PantallaActividades(
    uiState: ListadoUiState,
    operacion: OperacionUiState,
    busqueda: String,
    ordenActual: String,
    filtroPrioridad: String,
    modoVisualizacion: String,
    onBuscar: (String) -> Unit,
    onCambiarOrden: (String) -> Unit,
    onCambiarFiltro: (String) -> Unit,
    onCambiarModo: (String) -> Unit,
    onCrear: () -> Unit,
    onEditar: (ActividadFormativa) -> Unit,
    onEliminar: (ActividadFormativa) -> Unit,
    onReintentar: () -> Unit,
    onSimularError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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

            OutlinedTextField(
                value = busqueda,
                onValueChange = onBuscar,
                label = { Text("Buscar por título") },
                supportingText = { Text("La búsqueda más reciente reemplaza la anterior") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Selector("FECHA", "Días", ordenActual, onCambiarOrden, Modifier.weight(1f))
                Selector("TITULO", "Título", ordenActual, onCambiarOrden, Modifier.weight(1f))
                Selector("PROGRESO", "Progreso", ordenActual, onCambiarOrden, Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("TODAS" to "Todas", "BAJA" to "Baja", "MEDIA" to "Media", "ALTA" to "Alta").forEach { (valor, texto) ->
                    Selector(valor, texto, filtroPrioridad, onCambiarFiltro, Modifier.weight(1f))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { onCambiarModo(if (modoVisualizacion == "DETALLADO") "COMPACTO" else "DETALLADO") }, modifier = Modifier.weight(1f)) {
                    Text(if (modoVisualizacion == "DETALLADO") "Vista detallada" else "Vista compacta")
                }
            }

            if (actividades.isEmpty()) {

                EstadoVacio(
                    modifier = Modifier.weight(1f)
                )
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
            .fillMaxWidth()
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

@Composable private fun EstadoVacio(onCrear: () -> Unit, modifier: Modifier) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("No hay actividades para mostrar", style = MaterialTheme.typography.titleLarge)
        Text("Crea una actividad o cambia la búsqueda y el filtro.")
        Button(onClick = onCrear, modifier = Modifier.padding(top = 12.dp)) { Text("Crear actividad") }
    }
}

@Composable private fun EstadoError(mensaje: String, onReintentar: () -> Unit, onSimularError: () -> Unit, modifier: Modifier) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("No pudimos cargar la información", style = MaterialTheme.typography.titleLarge)
        Text(mensaje)
        Button(onClick = onReintentar, modifier = Modifier.padding(top = 12.dp)) { Text("Reintentar") }
        OutlinedButton(onClick = onSimularError) { Text("Repetir error de prueba") }
    }
}

    MiFormacionCTMATheme {
        PantallaActividades(
            actividades = emptyList()
        )
    }
}
