package com.sofia.miformacionctma

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
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

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val container =
            AppContainer(
                applicationContext
            )

        setContent {

            MiFormacionCTMATheme {

                Surface(
                    modifier =
                        Modifier.fillMaxSize(),

                    color =
                        MaterialTheme
                            .colorScheme
                            .background
                ) {

                    val navController =
                        rememberNavController()

                    // =====================================================
                    // VIEWMODEL DE ACTIVIDADES
                    // =====================================================

                    val viewModel: ActividadesViewModel =
                        viewModel(
                            factory =
                                ActividadesViewModelFactory(
                                    repository =
                                        container
                                            .actividadRepository,

                                    preferenciasRepository =
                                        container
                                            .preferenciasRepository
                                )
                        )

                    // =====================================================
                    // VIEWMODEL DE EVIDENCIAS
                    // =====================================================

                    val evidenciaViewModel: EvidenciaViewModel =
                        viewModel(
                            factory =
                                EvidenciaViewModelFactory(
                                    repository =
                                        container
                                            .evidenciaRepository
                                )
                        )

                    // =====================================================
                    // ESTADOS DE ACTIVIDADES
                    // =====================================================

                    val uiState by
                    viewModel
                        .uiState
                        .collectAsState()

                    val operacionUiState by
                    viewModel
                        .operacionUiState
                        .collectAsState()

                    val textoBusqueda by
                    viewModel
                        .textoBusqueda
                        .collectAsState()

                    val preferencias by
                    viewModel
                        .preferencias
                        .collectAsState()

                    val actualizacionUiState by
                    viewModel
                        .actualizacionUiState
                        .collectAsState()

                    // =====================================================
                    // ESTADOS DE EVIDENCIAS
                    // =====================================================

                    val evidenciaUiState by
                    evidenciaViewModel
                        .uiState
                        .collectAsState()

                    val operacionEvidenciaUiState by
                    evidenciaViewModel
                        .operacionUiState
                        .collectAsState()

                    // =====================================================
                    // PERMISO DE NOTIFICACIONES - SEMANA 9
                    // =====================================================

                    val permisoNotificacionesLauncher =
                        rememberLauncherForActivityResult(
                            contract =
                                ActivityResultContracts
                                    .RequestPermission()
                        ) { concedido ->

                            viewModel
                                .guardarRecordatoriosActivos(
                                    concedido
                                )
                        }

                    val permisoNotificacionesConcedido =
                        Build.VERSION.SDK_INT <
                                Build.VERSION_CODES.TIRAMISU ||
                                ContextCompat.checkSelfPermission(
                                    this@MainActivity,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) ==
                                PackageManager.PERMISSION_GRANTED

                    fun cambiarRecordatorios(
                        activar: Boolean
                    ) {

                        // Desactivar nunca necesita permiso.
                        if (!activar) {

                            viewModel
                                .guardarRecordatoriosActivos(
                                    false
                                )

                            return
                        }

                        // Android 12 o anterior no necesita
                        // POST_NOTIFICATIONS.
                        if (
                            Build.VERSION.SDK_INT <
                            Build.VERSION_CODES.TIRAMISU
                        ) {

                            viewModel
                                .guardarRecordatoriosActivos(
                                    true
                                )

                            return
                        }

                        // Si ya está concedido, activamos.
                        if (
                            permisoNotificacionesConcedido
                        ) {

                            viewModel
                                .guardarRecordatoriosActivos(
                                    true
                                )

                            return
                        }

                        // Si ya se solicitó antes y fue negado,
                        // no repetimos el diálogo.
                        if (
                            preferencias
                                .permisoNotificacionesSolicitado
                        ) {

                            viewModel
                                .guardarRecordatoriosActivos(
                                    false
                                )

                            return
                        }

                        // Guardamos que ya lo solicitamos.
                        viewModel
                            .marcarPermisoNotificacionesSolicitado()

                        permisoNotificacionesLauncher
                            .launch(
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                    }

                    // =====================================================
                    // NAVEGACIÓN
                    // =====================================================

                    NavHost(
                        navController =
                            navController,

                        startDestination =
                            "lista"
                    ) {

                        // =================================================
                        // LISTA
                        // =================================================

                        composable(
                            "lista"
                        ) {

                            PantallaActividadesSemana7(
                                uiState =
                                    uiState,

                                operacionUiState =
                                    operacionUiState,

                                actualizacionUiState =
                                    actualizacionUiState,

                                textoBusqueda =
                                    textoBusqueda,

                                orden =
                                    preferencias.orden,

                                modoVisualizacion =
                                    preferencias
                                        .modoVisualizacion,

                                onBusquedaChange =
                                    viewModel::cambiarBusqueda,

                                onOrdenChange =
                                    viewModel::guardarOrden,

                                onModoChange =
                                    viewModel::
                                    guardarModoVisualizacion,

                                onActividadClick = {
                                        actividad ->

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

                        composable(
                            "crear"
                        ) {

                            var formularioState by
                            remember {

                                mutableStateOf(
                                    FormularioActividadUiState()
                                )
                            }

                            FormularioActividad(
                                uiState =
                                    formularioState,

                                onTituloChange = {
                                        nuevoTitulo ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                titulo =
                                                    nuevoTitulo
                                            )
                                        )
                                },

                                onDescripcionChange = {
                                        nuevaDescripcion ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                descripcion =
                                                    nuevaDescripcion
                                            )
                                        )
                                },

                                onFechaChange = {
                                        nuevaFecha ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                fecha =
                                                    nuevaFecha
                                            )
                                        )
                                },

                                onPrioridadChange = {
                                        nuevaPrioridad ->

                                    formularioState =
                                        validarFormularioActividad(
                                            formularioState.copy(
                                                prioridad =
                                                    nuevaPrioridad
                                            )
                                        )
                                },

                                onProgresoChange = {
                                        nuevoProgreso ->

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
                        // DETALLE
                        // =================================================

                        composable(
                            route =
                                "detalle/{id}"
                        ) { backStackEntry ->

                            val id =
                                backStackEntry
                                    .arguments
                                    ?.getString(
                                        "id"
                                    )
                                    ?.toLongOrNull()

                            val actividad =
                                id?.let {
                                        actividadId ->

                                    viewModel.buscar(
                                        actividadId
                                    )
                                }

                            LaunchedEffect(
                                id
                            ) {

                                if (
                                    id != null
                                ) {

                                    evidenciaViewModel
                                        .cargarActividad(
                                            id
                                        )
                                }
                            }

                            DetalleActividadScreen(
                                actividad =
                                    actividad,

                                evidenciaUiState =
                                    evidenciaUiState,

                                operacionEvidenciaUiState =
                                    operacionEvidenciaUiState,

                                // =========================================
                                // RECORDATORIOS
                                // =========================================

                                recordatoriosActivos =
                                    preferencias
                                        .recordatoriosActivos &&
                                            permisoNotificacionesConcedido,

                                onRecordatoriosChange = {
                                        activar ->

                                    cambiarRecordatorios(
                                        activar
                                    )
                                },

                                // =========================================
                                // GUARDAR EVIDENCIA
                                // =========================================

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

                                // =========================================
                                // ELIMINAR EVIDENCIA
                                // =========================================

                                onEliminarEvidencia = {
                                        actividadId ->

                                    evidenciaViewModel
                                        .eliminar(
                                            actividadId
                                        )
                                },

                                // =========================================
                                // SINCRONIZAR
                                // =========================================

                                onSincronizarEvidencia = {
                                        actividadId ->

                                    evidenciaViewModel
                                        .sincronizar(
                                            actividadId
                                        )
                                },

                                // =========================================
                                // REINTENTAR
                                // =========================================

                                onReintentarSincronizacion = {
                                        actividadId ->

                                    evidenciaViewModel
                                        .reintentarSincronizacion(
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