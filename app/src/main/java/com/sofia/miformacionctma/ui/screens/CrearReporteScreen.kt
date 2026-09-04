package com.sofia.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CrearReporteRoute(
    viewModel: CrearReporteViewModel,
    onGuardado: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.guardadoId) {
        uiState.guardadoId?.let(onGuardado)
    }

    CrearReporteContent(
        uiState = uiState,
        onTituloChange = viewModel::actualizarTitulo,
        onGuardar = viewModel::guardar
    )
}

@Composable
fun CrearReporteContent(
    uiState: CrearUiState,
    onTituloChange: (String) -> Unit,
    onGuardar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Crear reporte",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = uiState.titulo,
            onValueChange = onTituloChange,
            label = { Text("Título") },
            isError = uiState.errorTitulo != null,
            supportingText = {
                uiState.errorTitulo?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGuardar,
            enabled = !uiState.guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.guardando) {
                CircularProgressIndicator()
            } else {
                Text("Guardar")
            }
        }

        uiState.guardadoId?.let { id ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Reporte guardado: $id")
        }
    }
}
