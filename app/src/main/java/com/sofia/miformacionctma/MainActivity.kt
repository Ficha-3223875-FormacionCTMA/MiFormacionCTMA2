package com.sofia.miformacionctma

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sofia.miformacionctma.data.local.DatabaseProvider
import com.sofia.miformacionctma.data.preferences.PreferencesRepository
import com.sofia.miformacionctma.data.remote.NetworkProvider
import com.sofia.miformacionctma.data.remote.RemoteActividadDataSource
import com.sofia.miformacionctma.data.remote.session.SessionTokenProvider
import com.sofia.miformacionctma.data.repository.ActividadRepository
import com.sofia.miformacionctma.data.repository.EvidenciaRepository
import com.sofia.miformacionctma.device.nuevaEvidenciaUri
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.ui.screens.PantallaActividades
import com.sofia.miformacionctma.ui.screens.PantallaCrearActividad
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme
import com.sofia.miformacionctma.ui.viewmodel.ActividadViewModel
import com.sofia.miformacionctma.ui.viewmodel.ActividadViewModelFactory
import com.sofia.miformacionctma.ui.viewmodel.EvidenciaViewModel
import com.sofia.miformacionctma.ui.viewmodel.EvidenciaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = DatabaseProvider.getDatabase(applicationContext)
        val tokenProvider = SessionTokenProvider()
        val api = NetworkProvider.api(tokenProvider)
        val actividadFactory = ActividadViewModelFactory(
            repository = ActividadRepository(database.actividadDao(), RemoteActividadDataSource(api)),
            preferencesRepository = PreferencesRepository(applicationContext)
        )
        val evidenciaFactory = EvidenciaViewModelFactory(
            EvidenciaRepository(database.evidenciaDao(), contentResolver, api)
        )

        setContent {
            MiFormacionCTMATheme {
                val vm: ActividadViewModel = viewModel(factory = actividadFactory)
                val evidenciaVm: EvidenciaViewModel = viewModel(factory = evidenciaFactory)
                val uiState by vm.uiState.collectAsStateWithLifecycle()
                val operacion by vm.operacion.collectAsStateWithLifecycle()
                val refresh by vm.refresh.collectAsStateWithLifecycle()
                val preferencias by vm.preferencias.collectAsStateWithLifecycle()
                val busqueda by vm.busqueda.collectAsStateWithLifecycle()
                val evidencias by evidenciaVm.evidencias.collectAsStateWithLifecycle()
                val evidenciaEstado by evidenciaVm.estado.collectAsStateWithLifecycle()

                var mostrarFormulario by rememberSaveable { mutableStateOf(false) }
                var actividadEditar by remember { mutableStateOf<ActividadFormativa?>(null) }
                var actividadEvidenciaId by remember { mutableLongStateOf(0L) }
                var cameraUri by remember { mutableStateOf<android.net.Uri?>(null) }

                val picker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    if (uri != null && actividadEvidenciaId > 0) evidenciaVm.guardar(actividadEvidenciaId, uri)
                }
                val camera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
                    val uri = cameraUri
                    if (ok && uri != null && actividadEvidenciaId > 0) evidenciaVm.guardar(actividadEvidenciaId, uri)
                }
                val notifications = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { }

                if (mostrarFormulario) {
                    PantallaCrearActividad(
                        actividadEditar = actividadEditar,
                        onGuardar = { actividad ->
                            if (actividadEditar == null) vm.insertar(actividad) else vm.actualizar(actividad)
                            actividadEditar = null
                            mostrarFormulario = false
                        },
                        onCancelar = { actividadEditar = null; mostrarFormulario = false }
                    )
                } else {
                    PantallaActividades(
                        uiState = uiState,
                        operacion = operacion,
                        refresh = refresh,
                        evidenciaEstado = evidenciaEstado,
                        evidencias = evidencias.associateBy { it.actividadId },
                        busqueda = busqueda,
                        ordenActual = preferencias.orden,
                        filtroPrioridad = preferencias.filtroPrioridad,
                        modoVisualizacion = preferencias.modoVisualizacion,
                        onBuscar = vm::cambiarBusqueda,
                        onCambiarOrden = vm::guardarOrden,
                        onCambiarFiltro = vm::guardarFiltroPrioridad,
                        onCambiarModo = vm::guardarModoVisualizacion,
                        onCrear = { actividadEditar = null; mostrarFormulario = true },
                        onEditar = { actividadEditar = it; mostrarFormulario = true },
                        onEliminar = vm::eliminar,
                        onReintentar = vm::reintentar,
                        onSimularError = vm::simularError,
                        onRefresh = vm::actualizarDesdeRed,
                        onElegirImagen = { id ->
                            actividadEvidenciaId = id
                            picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        onTomarFoto = { id ->
                            actividadEvidenciaId = id
                            val uri = nuevaEvidenciaUri(this)
                            cameraUri = uri
                            camera.launch(uri)
                        },
                        onEliminarEvidencia = evidenciaVm::eliminar,
                        onSubirEvidencia = evidenciaVm::subir,
                        onActivarRecordatorios = {
                            if (Build.VERSION.SDK_INT >= 33) notifications.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    )
                }
            }
        }
    }
}
