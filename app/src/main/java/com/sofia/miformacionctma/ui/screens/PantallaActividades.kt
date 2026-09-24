package com.sofia.miformacionctma.ui.screens

import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.viewinterop.AndroidView
import com.sofia.miformacionctma.data.local.entity.EvidenciaEntity
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.ui.components.TarjetaActividad
import com.sofia.miformacionctma.ui.viewmodel.EvidenciaUiState
import com.sofia.miformacionctma.ui.viewmodel.ListadoUiState
import com.sofia.miformacionctma.ui.viewmodel.OperacionUiState
import com.sofia.miformacionctma.ui.viewmodel.RefreshUiState

@Composable
fun PantallaActividades(
    uiState: ListadoUiState,
    operacion: OperacionUiState,
    refresh: RefreshUiState,
    evidenciaEstado: EvidenciaUiState,
    evidencias: Map<Long, EvidenciaEntity>,
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
    onRefresh: () -> Unit,
    onElegirImagen: (Long) -> Unit,
    onTomarFoto: (Long) -> Unit,
    onEliminarEvidencia: (Long) -> Unit,
    onSubirEvidencia: (EvidenciaEntity) -> Unit,
    onActivarRecordatorios: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Mi Formación CTMA", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 12.dp))
            Text("Semanas 7–9 · Estado reactivo, servicios web y evidencia segura", style = MaterialTheme.typography.titleSmall)

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onCrear, enabled = operacion !is OperacionUiState.EnCurso, modifier = Modifier.weight(1f)) { Text("Nueva actividad") }
                OutlinedButton(onClick = onRefresh, enabled = refresh !is RefreshUiState.EnCurso, modifier = Modifier.weight(1f)) { Text("Actualizar API") }
            }

            EstadoRefresh(refresh)
            EstadoEvidencia(evidenciaEstado)

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
                OutlinedButton(onClick = onActivarRecordatorios, modifier = Modifier.weight(1f)) { Text("Recordatorios") }
            }

            EstadoOperacion(operacion)
            when (uiState) {
                ListadoUiState.Cargando -> EstadoCargando(Modifier.weight(1f))
                ListadoUiState.Vacio -> EstadoVacio(onCrear, Modifier.weight(1f))
                is ListadoUiState.Error -> EstadoError(uiState.mensaje, onReintentar, onSimularError, Modifier.weight(1f))
                is ListadoUiState.Contenido -> ListaActividades(
                    actividades = uiState.actividades,
                    evidencias = evidencias,
                    modoVisualizacion = modoVisualizacion,
                    onEditar = onEditar,
                    onEliminar = onEliminar,
                    onElegirImagen = onElegirImagen,
                    onTomarFoto = onTomarFoto,
                    onEliminarEvidencia = onEliminarEvidencia,
                    onSubirEvidencia = onSubirEvidencia,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable private fun Selector(valor: String, texto: String, actual: String, onCambiar: (String) -> Unit, modifier: Modifier) {
    OutlinedButton(onClick = { onCambiar(valor) }, modifier = modifier) { Text(if (actual == valor) "✓ $texto" else texto) }
}

@Composable private fun EstadoOperacion(estado: OperacionUiState) { when (estado) {
    OperacionUiState.Inactiva -> Unit
    OperacionUiState.EnCurso -> Text("Operación en curso…")
    is OperacionUiState.Exitosa -> Text("✓ ${estado.mensaje}")
    is OperacionUiState.Fallida -> Text("Error: ${estado.mensaje}")
} }

@Composable private fun EstadoRefresh(estado: RefreshUiState) { when (estado) {
    RefreshUiState.Inactiva -> Unit
    RefreshUiState.EnCurso -> Text("Actualizando desde el servicio…")
    is RefreshUiState.Exitosa -> Text("✓ ${estado.mensaje}")
    is RefreshUiState.Fallida -> Text("Actualización fallida: ${estado.mensaje}. Los datos locales se conservan.")
} }

@Composable private fun EstadoEvidencia(estado: EvidenciaUiState) { when (estado) {
    EvidenciaUiState.Inactiva -> Unit
    EvidenciaUiState.Procesando -> Text("Procesando evidencia…")
    is EvidenciaUiState.Mensaje -> Text("✓ ${estado.texto}")
    is EvidenciaUiState.Error -> Text("Evidencia: ${estado.texto}")
} }

@Composable private fun EstadoCargando(modifier: Modifier) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator(Modifier.semantics { contentDescription = "Cargando actividades" })
        Text("Consultando actividades…", modifier = Modifier.padding(top = 12.dp))
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

@Composable
private fun ListaActividades(
    actividades: List<ActividadFormativa>, evidencias: Map<Long, EvidenciaEntity>, modoVisualizacion: String,
    onEditar: (ActividadFormativa) -> Unit, onEliminar: (ActividadFormativa) -> Unit,
    onElegirImagen: (Long) -> Unit, onTomarFoto: (Long) -> Unit, onEliminarEvidencia: (Long) -> Unit,
    onSubirEvidencia: (EvidenciaEntity) -> Unit, modifier: Modifier
) {
    LazyColumn(modifier.fillMaxWidth(), contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(actividades, key = { it.id }) { actividad ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                if (modoVisualizacion == "COMPACTO") {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) { Text(actividad.titulo, style = MaterialTheme.typography.titleMedium); Text("${actividad.progreso}% · ${actividad.diasRestantes} días · ${actividad.prioridad}") }
                        OutlinedButton(onClick = { onEditar(actividad) }) { Text("Editar") }
                    }
                } else TarjetaActividad(actividad, onEditar, onEliminar)

                val evidencia = evidencias[actividad.id]
                if (evidencia != null) {
                    AndroidView(
                        factory = { context -> ImageView(context).apply { scaleType = ImageView.ScaleType.CENTER_CROP } },
                        update = { it.setImageURI(android.net.Uri.parse(evidencia.uri)) },
                        modifier = Modifier.fillMaxWidth().height(140.dp).semantics { contentDescription = "Vista previa de evidencia de ${actividad.titulo}" }
                    )
                    Text("Evidencia: ${evidencia.estado} · ${evidencia.tamanoBytes / 1024} KB")
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(onClick = { onElegirImagen(actividad.id) }, modifier = Modifier.weight(1f)) { Text("Reemplazar") }
                        OutlinedButton(onClick = { onSubirEvidencia(evidencia) }, modifier = Modifier.weight(1f)) { Text("Sincronizar") }
                        OutlinedButton(onClick = { onEliminarEvidencia(actividad.id) }, modifier = Modifier.weight(1f)) { Text("Eliminar foto") }
                    }
                } else {
                    Text("Evidencia fotográfica opcional: revisa la imagen antes de sincronizarla.")
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(onClick = { onElegirImagen(actividad.id) }, modifier = Modifier.weight(1f)) { Text("Elegir imagen") }
                        OutlinedButton(onClick = { onTomarFoto(actividad.id) }, modifier = Modifier.weight(1f)) { Text("Tomar foto") }
                    }
                }
            }
        }
    }
}
