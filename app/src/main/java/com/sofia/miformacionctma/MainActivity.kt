package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sofia.miformacionctma.data.local.DatabaseProvider
import com.sofia.miformacionctma.data.preferences.PreferencesRepository
import com.sofia.miformacionctma.data.repository.ActividadRepository
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.ui.screens.PantallaActividades
import com.sofia.miformacionctma.ui.screens.PantallaCrearActividad
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme
import com.sofia.miformacionctma.ui.viewmodel.ActividadViewModel
import com.sofia.miformacionctma.ui.viewmodel.ActividadViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val database = DatabaseProvider.getDatabase(applicationContext)

        val actividadRepository = ActividadRepository(
            actividadDao = database.actividadDao()
        )

        val preferencesRepository = PreferencesRepository(
            context = applicationContext
        )

        val viewModelFactory = ActividadViewModelFactory(
            repository = actividadRepository,
            preferencesRepository = preferencesRepository
        )

        setContent {

            MiFormacionCTMATheme {

                val actividadViewModel: ActividadViewModel = viewModel(
                    factory = viewModelFactory
                )

                val actividades by actividadViewModel.actividades.collectAsState()

                val ordenActual by
                actividadViewModel.ordenActividades.collectAsState()

                var mostrarFormulario by remember {
                    mutableStateOf(false)
                }

                var actividadEditar by remember {
                    mutableStateOf<ActividadFormativa?>(null)
                }

                if (mostrarFormulario) {

                    PantallaCrearActividad(
                        actividadEditar = actividadEditar,

                        onGuardar = { actividad ->

                            if (actividadEditar == null) {
                                actividadViewModel.insertar(actividad)
                            } else {
                                actividadViewModel.actualizar(actividad)
                            }

                            actividadEditar = null
                            mostrarFormulario = false
                        },

                        onCancelar = {
                            actividadEditar = null
                            mostrarFormulario = false
                        }
                    )

                } else {

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Button(
                            onClick = {
                                actividadEditar = null
                                mostrarFormulario = true
                            },
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 16.dp
                            )
                        ) {
                            Text("Nueva actividad")
                        }

                        PantallaActividades(
                            actividades = actividades,
                            ordenActual = ordenActual,

                            onCambiarOrden = { nuevoOrden ->
                                actividadViewModel.guardarOrden(
                                    nuevoOrden
                                )
                            },

                            onEditar = { actividad ->
                                actividadEditar = actividad
                                mostrarFormulario = true
                            },

                            onEliminar = { actividad ->
                                actividadViewModel.eliminar(actividad)
                            },

                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}