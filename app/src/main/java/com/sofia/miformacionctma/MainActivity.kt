package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sofia.miformacionctma.data.container.AppContainer
import com.sofia.miformacionctma.ui.screens.ActividadesViewModel
import com.sofia.miformacionctma.ui.screens.ActividadesViewModelFactory
import com.sofia.miformacionctma.ui.screens.PantallaActividadesSemana7
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val container = AppContainer(applicationContext)

        setContent {

            MiFormacionCTMATheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    val viewModel: ActividadesViewModel = viewModel(
                        factory = ActividadesViewModelFactory(
                            repository = container.actividadRepository,
                            preferenciasRepository = container.preferenciasRepository
                        )
                    )

                    val uiState by viewModel.uiState.collectAsState()

                    val operacionUiState by
                    viewModel.operacionUiState.collectAsState()

                    val textoBusqueda by
                    viewModel.textoBusqueda.collectAsState()

                    val preferencias by
                    viewModel.preferencias.collectAsState()

                    val actualizacionUiState by
                    viewModel.actualizacionUiState.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = "lista"
                    ) {

                        composable("lista") {

                            PantallaActividadesSemana7(
                                uiState = uiState,
                                operacionUiState = operacionUiState,
                                actualizacionUiState = actualizacionUiState,

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
                                    navController.navigate("crear")
                                },

                                onReintentar =
                                    viewModel::reintentar,

                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        composable("crear") {

                            // Mantén aquí tu pantalla actual de
                            // Crear actividad.
                            //
                            // Si actualmente tienes código aquí,
                            // NO lo reemplaces por una pantalla nueva.
                        }

                        composable("detalle/{id}") {

                            // Mantén aquí tu pantalla actual de
                            // Detalle.
                            //
                            // Si actualmente tienes código aquí,
                            // NO lo reemplaces por una pantalla nueva.
                        }
                    }
                }
            }
        }
    }
}