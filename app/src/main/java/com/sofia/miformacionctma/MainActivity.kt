package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sofia.miformacionctma.data.container.AppContainer
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.ui.screens.ActividadesViewModel
import com.sofia.miformacionctma.ui.screens.ActividadesViewModelFactory
import com.sofia.miformacionctma.ui.screens.DetalleActividadScreen
import com.sofia.miformacionctma.ui.screens.FormularioActividad
import com.sofia.miformacionctma.ui.screens.PantallaActividadesSemana7
import com.sofia.miformacionctma.ui.screens.validarFormularioActividad
import com.sofia.miformacionctma.ui.state.FormularioActividadUiState
import com.sofia.miformacionctma.ui.state.ListadoUiState
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiFormacionCTMATheme {
                MiFormacionApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiFormacionApp() {
    val context = LocalContext.current
    val appContainer = remember {
        AppContainer(context.applicationContext)
    }

    val viewModel: ActividadesViewModel = viewModel(
        factory = ActividadesViewModelFactory(
            appContainer.actividadRepository,
            appContainer.preferenciasRepository
        )
    )

    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val operacionUiState by viewModel.operacionUiState.collectAsStateWithLifecycle()
    val textoBusqueda by viewModel.textoBusqueda.collectAsStateWithLifecycle()
    val preferencias by viewModel.preferencias.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {
        composable("lista") {
            PantallaActividadesSemana7(
                uiState = uiState,
                operacionUiState = operacionUiState,
                textoBusqueda = textoBusqueda,
                orden = preferencias.orden,
                modoVisualizacion = preferencias.modoVisualizacion,
                onBusquedaChange = viewModel::cambiarBusqueda,
                onOrdenChange = viewModel::guardarOrden,
                onModoChange = viewModel::guardarModoVisualizacion,
                onActividadClick = { actividad ->
                    navController.navigate("detalle/${actividad.id}")
                },
                onNuevaActividad = {
                    navController.navigate("crear")
                },
                onReintentar = viewModel::reintentar,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable("crear") {
            var state by rememberSaveable(
                stateSaver = FormularioActividadStateSaver
            ) {
                mutableStateOf(FormularioActividadUiState())
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Nueva actividad") }
                    )
                }
            ) { padding ->
                FormularioActividad(
                    uiState = state,
                    onTituloChange = {
                        state = validarFormularioActividad(
                            state.copy(titulo = it.take(80))
                        )
                    },
                    onDescripcionChange = {
                        state = validarFormularioActividad(
                            state.copy(descripcion = it.take(241))
                        )
                    },
                    onFechaChange = {
                        state = validarFormularioActividad(
                            state.copy(fecha = it)
                        )
                    },
                    onPrioridadChange = {
                        state = state.copy(prioridad = it)
                    },
                    onProgresoChange = {
                        state = validarFormularioActividad(
                            state.copy(
                                progreso = it.filter(Char::isDigit).take(3)
                            )
                        )
                    },
                    onGuardar = {
                        if (state.puedeGuardar) {
                            viewModel.agregar(state)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.padding(padding)
                )
            }
        }

        composable(
            route = "detalle/{actividadId}",
            arguments = listOf(
                navArgument("actividadId") {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("actividadId")

            DetalleActividadScreen(
                actividad = id?.let { viewModel.buscar(it) },
                onBack = {
                    navController.popBackStack()
                },
                onEliminar = { actividadId ->
                    viewModel.eliminar(actividadId)
                    navController.popBackStack()
                }
            )
        }
    }
}

private val FormularioActividadStateSaver =
    Saver<FormularioActividadUiState, List<String>>(
        save = {
            listOf(
                it.titulo,
                it.descripcion,
                it.fecha,
                it.prioridad.name,
                it.progreso
            )
        },
        restore = { v ->
            FormularioActividadUiState(
                titulo = v.getOrElse(0) { "" },
                descripcion = v.getOrElse(1) { "" },
                fecha = v.getOrElse(2) { "" },
                prioridad = runCatching {
                    Prioridad.valueOf(
                        v.getOrElse(3) { "MEDIA" }
                    )
                }.getOrDefault(Prioridad.MEDIA),
                progreso = v.getOrElse(4) { "0" }
            )
        }
    )
