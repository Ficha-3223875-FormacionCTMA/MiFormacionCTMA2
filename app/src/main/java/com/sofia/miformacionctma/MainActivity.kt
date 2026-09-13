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
import com.sofia.miformacionctma.ui.screens.PantallaActividades
import com.sofia.miformacionctma.ui.screens.validarFormularioActividad
import com.sofia.miformacionctma.ui.state.FormularioActividadUiState
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
            appContainer.actividadRepository
        )
    )

    val navController = rememberNavController()

    val actividades by viewModel.actividades.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        /*
         * LISTA DE ACTIVIDADES
         */
        composable("lista") {

            PantallaActividades(
                actividades = actividades,

                onActividadClick = { actividad ->
                    navController.navigate(
                        "detalle/${actividad.id}"
                    )
                },

                onEmptyAction = {
                    navController.navigate("crear")
                },

                modifier = Modifier.fillMaxSize()
            )
        }


        /*
         * CREAR NUEVA ACTIVIDAD
         */
        composable("crear") {

            var state by rememberSaveable(
                stateSaver = FormularioActividadStateSaver
            ) {
                mutableStateOf(
                    FormularioActividadUiState()
                )
            }

            Scaffold(

                topBar = {

                    TopAppBar(
                        title = {
                            Text("Nueva actividad")
                        }
                    )
                }

            ) { padding ->

                FormularioActividad(

                    uiState = state,

                    onTituloChange = {

                        state = validarFormularioActividad(
                            state.copy(
                                titulo = it.take(80)
                            )
                        )
                    },

                    onDescripcionChange = {

                        state = validarFormularioActividad(
                            state.copy(
                                descripcion = it.take(241)
                            )
                        )
                    },

                    onFechaChange = {

                        state = validarFormularioActividad(
                            state.copy(
                                fecha = it
                            )
                        )
                    },

                    onPrioridadChange = {

                        state = state.copy(
                            prioridad = it
                        )
                    },

                    onProgresoChange = {

                        state = validarFormularioActividad(
                            state.copy(
                                progreso = it
                                    .filter(Char::isDigit)
                                    .take(3)
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


        /*
         * DETALLE DE ACTIVIDAD
         */
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
                actividad = id?.let {
                    viewModel.buscar(it)
                },

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


/*
 * Saver para conservar temporalmente
 * los datos escritos en el formulario.
 */
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

                titulo =
                    v.getOrElse(0) {
                        ""
                    },

                descripcion =
                    v.getOrElse(1) {
                        ""
                    },

                fecha =
                    v.getOrElse(2) {
                        ""
                    },

                prioridad =
                    runCatching {

                        Prioridad.valueOf(
                            v.getOrElse(3) {
                                "MEDIA"
                            }
                        )

                    }.getOrDefault(
                        Prioridad.MEDIA
                    ),

                progreso =
                    v.getOrElse(4) {
                        "0"
                    }
            )
        }
    )