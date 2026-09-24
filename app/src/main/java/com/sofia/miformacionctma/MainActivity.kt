package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sofia.miformacionctma.data.container.AppContainer
import com.sofia.miformacionctma.ui.screens.ActividadesViewModel
import com.sofia.miformacionctma.ui.screens.ActividadesViewModelFactory
import com.sofia.miformacionctma.ui.screens.DetalleActividadScreen
import com.sofia.miformacionctma.ui.screens.EvidenciaViewModel
import com.sofia.miformacionctma.ui.screens.EvidenciaViewModelFactory
import com.sofia.miformacionctma.ui.screens.FormularioActividad
import com.sofia.miformacionctma.ui.screens.PantallaActividadesSemana7
import com.sofia.miformacionctma.ui.screens.validarFormularioActividad
import com.sofia.miformacionctma.ui.state.FormularioActividadUiState
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val container =
            AppContainer(applicationContext)

        setContent {

            MiFormacionCTMATheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController =
                        rememberNavController()

                    // =====================================================
                    // VIEWMODEL DE ACTIVIDADES
                    // =====================================================

                    val viewModel: ActividadesViewModel =
                        viewModel(
                            factory = ActividadesViewModelFactory(
                                repository =
                                    container.actividadRepository,
                                preferenciasRepository =
                                    container.preferenciasRepository
                            )
                        )

                    // =====================================================
                    // VIEWMODEL DE EVIDENCIAS - SEMANA 9
                    // =====================================================

                    val evidenciaViewModel: EvidenciaViewModel =
                        viewModel(
                            factory = EvidenciaViewModelFactory(
                                repository =
                                    container.evidenciaRepository
                            )
                        )

                    // =====================================================
                    // ESTADOS DE ACTIVIDADES
                    // =====================================================

                    val uiState by
                    viewModel.uiState.collectAsState()

                    val operacionUiState by
                    viewModel.operacionUiState.collectAsState()

                    val textoBusqueda by
                    viewModel.textoBusqueda.collectAsState()

                    val preferencias by
                    viewModel.preferencias.collectAsState()

                    val actualizacionUiState by
                    viewModel.actualizacionUiState.collectAsState()

                    // =====================================================
                    // ESTADOS DE EVIDENCIAS
                    // =====================================================

                    val evidenciaUiState by
                    evidenciaViewModel.uiState.collectAsState()

                    val operacionEvidenciaUiState by
                    evidenciaViewModel.operacionUiState.collectAsState()

                    // =====================================================
                    // NAVEGACIÓN
                    // =====================================================

                    NavHost(
                        navController = navController,
                        startDestination = "lista"
                    ) {

                        // =================================================
                        // LISTA
                        // =================================================

                        composable("lista") {

                            PantallaActividadesSemana7(
                                uiState = uiState,
                                operacionUiState = operacionUiState,
                                actualizacionUiState =
                                    actualizacionUiState,

                                textoBusqueda = textoBusqueda,

                                orden = preferencias.orden,

                                modoVisualizacion =
                                    preferencias.modoVisualizacion,

                                onBusquedaChange =
                                    viewModel::cambiarBusqueda,

                                onOrdenChange =
                                    viewModel::guardarOrden,

                                onModoChange =
                                    viewModel::guardarModoVisualizacion,

                                onActividadClick = { actividad ->

                                    navController.navigate(
                                        "detalle/${actividad.id}"
                                    )
                                },

                                onNuevaActividad = {

                                    navController.navigate(
                                        "crear"
                                    )
                                },

                                onReintentar =
                                    viewModel::reintentar,

                                modifier =
                                    Modifier.fillMaxSize()
                            )
                        }

                        // =================================================
                        // CREAR ACTIVIDAD
                        // =================================================

                        composable("crear") {

                            var formularioState by remember {
                                mutableStateOf(
                                    FormularioActividadUiState()
                                )
                            }

                            FormularioActividad(
                                uiState = formularioState,

                                onTituloChange = { nuevoTitulo ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                titulo = nuevoTitulo
                                            )
                                        )
                                },

                                onDescripcionChange = { nuevaDescripcion ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                descripcion =
                                                    nuevaDescripcion
                                            )
                                        )
                                },

                                onFechaChange = { nuevaFecha ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                fecha = nuevaFecha
                                            )
                                        )
                                },

                                onPrioridadChange = { nuevaPrioridad ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                prioridad =
                                                    nuevaPrioridad
                                            )
                                        )
                                },

                                onProgresoChange = { nuevoProgreso ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                progreso =
                                                    nuevoProgreso
                                            )
                                        )
                                },

                                onGuardar = {

                                    val formularioValidado =
                                        validarFormularioActividad(
                                            formularioState
                                        )

                                    formularioState =
                                        formularioValidado

                                    if (
                                        formularioValidado
                                            .puedeGuardar
                                    ) {

                                        viewModel.agregar(
                                            formularioValidado
                                        )

                                        navController
                                            .popBackStack()
                                    }
                                },

                                modifier =
                                    Modifier.fillMaxSize()
                            )
                        }

                        // =================================================
                        // DETALLE DE ACTIVIDAD
                        // =================================================

                        composable(
                            route = "detalle/{id}"
                        ) { backStackEntry ->

                            val id =
                                backStackEntry.arguments
                                    ?.getString("id")
                                    ?.toLongOrNull()

                            val actividad =
                                id?.let { actividadId ->

                                    viewModel.buscar(
                                        actividadId
                                    )
                                }

                            LaunchedEffect(id) {

                                if (id != null) {

                                    evidenciaViewModel
                                        .cargarActividad(id)
                                }
                            }

                            DetalleActividadScreen(
                                actividad = actividad,

                                evidenciaUiState =
                                    evidenciaUiState,

                                operacionEvidenciaUiState =
                                    operacionEvidenciaUiState,

                                onEvidenciaSeleccionada = {
                                        actividadId,
                                        uri,
                                        mimeType,
                                        tamanoBytes,
                                        nombreArchivo,
                                        archivoPropio ->

                                    evidenciaViewModel
                                        .registrarLocal(
                                            actividadId =
                                                actividadId,

                                            uri =
                                                uri,

                                            mimeType =
                                                mimeType,

                                            tamanoBytes =
                                                tamanoBytes,

                                            nombreArchivo =
                                                nombreArchivo,

                                            archivoPropio =
                                                archivoPropio
                                        )
                                },

                                onEliminarEvidencia = {
                                        actividadId ->

                                    evidenciaViewModel
                                        .eliminar(
                                            actividadId
                                        )
                                },

                                onBack = {

                                    navController
                                        .popBackStack()
                                },

                                onEliminar = {
                                        actividadId ->

                                    viewModel.eliminar(
                                        actividadId
                                    )

                                    navController
                                        .popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}