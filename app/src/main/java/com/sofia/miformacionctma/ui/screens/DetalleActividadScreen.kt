package com.sofia.miformacionctma.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import com.sofia.miformacionctma.data.EstadoEvidencia
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.estadoActividad
import java.io.File

@Composable
fun DetalleActividadScreen(
    actividad: ActividadFormativa?,
    onBack: () -> Unit,
    onEliminar: (Long) -> Unit,

    evidenciaUiState: EvidenciaUiState =
        EvidenciaUiState.SinEvidencia,

    operacionEvidenciaUiState: OperacionEvidenciaUiState =
        OperacionEvidenciaUiState.Inactiva,

    recordatoriosActivos: Boolean = false,

    onRecordatoriosChange: (Boolean) -> Unit = {},

    onEvidenciaSeleccionada: (
        actividadId: Long,
        uri: String,
        mimeType: String,
        tamanoBytes: Long,
        nombreArchivo: String,
        archivoPropio: Boolean
    ) -> Unit = { _, _, _, _, _, _ -> },

    onEliminarEvidencia: (Long) -> Unit = {},

    onSincronizarEvidencia: (Long) -> Unit = {},

    onReintentarSincronizacion: (Long) -> Unit = {}
) {

    val context =
        LocalContext.current

    var uriFotoPendiente by
    remember {
        mutableStateOf<Uri?>(null)
    }

    var archivoFotoPendiente by
    remember {
        mutableStateOf<File?>(null)
    }

    var mensajeDispositivo by
    remember {
        mutableStateOf<String?>(null)
    }

    // ============================================================
    // PHOTO PICKER
    // ============================================================

    val selectorImagen =
        rememberLauncherForActivityResult(
            contract = PickVisualMedia()
        ) { uri ->

            if (
                uri == null
            ) {

                mensajeDispositivo =
                    "Selección cancelada. La evidencia anterior se conserva."

            } else if (
                actividad != null
            ) {

                mensajeDispositivo =
                    null

                val resolver =
                    context.contentResolver

                runCatching {

                    resolver
                        .takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                }

                val mimeType =
                    resolver.getType(uri)
                        ?: ""

                val tamanoBytes =
                    obtenerTamanoUri(
                        uri = uri,
                        context = context
                    )

                val nombreArchivo =
                    generarNombreEvidencia(
                        actividadId =
                            actividad.id,
                        mimeType =
                            mimeType
                    )

                onEvidenciaSeleccionada(
                    actividad.id,
                    uri.toString(),
                    mimeType,
                    tamanoBytes,
                    nombreArchivo,
                    false
                )
            }
        }

    // ============================================================
    // CÁMARA
    // ============================================================

    val camara =
        rememberLauncherForActivityResult(
            contract = TakePicture()
        ) { exito ->

            val uri =
                uriFotoPendiente

            val archivo =
                archivoFotoPendiente

            if (
                exito &&
                uri != null &&
                archivo != null &&
                actividad != null
            ) {

                mensajeDispositivo =
                    null

                onEvidenciaSeleccionada(
                    actividad.id,
                    uri.toString(),
                    "image/jpeg",
                    archivo.length(),
                    archivo.name,
                    true
                )

            } else {

                if (
                    !exito
                ) {

                    mensajeDispositivo =
                        "Captura cancelada. La evidencia anterior se conserva."

                } else {

                    mensajeDispositivo =
                        "No fue posible procesar la foto. La evidencia anterior se conserva."
                }

                // Eliminamos únicamente el archivo
                // temporal nuevo creado para la cámara.
                archivo?.let {

                    runCatching {
                        it.delete()
                    }
                }
            }

            uriFotoPendiente =
                null

            archivoFotoPendiente =
                null
        }

    fun tomarFoto() {

        if (
            actividad == null
        ) {
            return
        }

        mensajeDispositivo =
            null

        val archivo =
            crearArchivoParaCamara(
                context = context,
                actividadId =
                    actividad.id
            )

        val uri =
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                archivo
            )

        archivoFotoPendiente =
            archivo

        uriFotoPendiente =
            uri

        runCatching {

            camara.launch(
                uri
            )

        }.onFailure {

            runCatching {
                archivo.delete()
            }

            archivoFotoPendiente =
                null

            uriFotoPendiente =
                null

            mensajeDispositivo =
                "No fue posible abrir la cámara. La evidencia anterior se conserva."
        }
    }

    // ============================================================
    // CONTENIDO
    // ============================================================

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp),

        verticalArrangement =
            Arrangement.spacedBy(
                12.dp
            )
    ) {

        if (
            actividad == null
        ) {

            Text(
                text =
                    "Actividad no encontrada",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Text(
                text =
                    "El identificador solicitado no corresponde a una actividad disponible."
            )

        } else {

            // ====================================================
            // DATOS DE LA ACTIVIDAD
            // ====================================================

            Text(
                text =
                    actividad.titulo,

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            actividad.descripcion
                ?.takeIf {
                    it.isNotBlank()
                }
                ?.let { descripcion ->

                    Text(
                        text =
                            descripcion
                    )
                }

            Text(
                text =
                    "Fecha: ${
                        actividad.fecha.ifBlank {
                            "Sin fecha"
                        }
                    }"
            )

            Text(
                text =
                    "Estado: ${
                        estadoActividad(
                            actividad
                        )
                    }"
            )

            Text(
                text =
                    "Progreso: ${actividad.progreso}%"
            )

            Text(
                text =
                    "Prioridad: ${actividad.prioridad.name}"
            )

            // ====================================================
            // RECORDATORIOS - SEMANA 9
            // ====================================================

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            Text(
                text =
                    if (
                        recordatoriosActivos
                    ) {

                        "Recordatorios: activados"

                    } else {

                        "Recordatorios: desactivados"
                    }
            )

            OutlinedButton(
                modifier =
                    Modifier.fillMaxWidth(),

                onClick = {

                    onRecordatoriosChange(
                        !recordatoriosActivos
                    )
                }
            ) {

                Text(
                    text =
                        if (
                            recordatoriosActivos
                        ) {

                            "Desactivar recordatorios"

                        } else {

                            "Activar recordatorios"
                        }
                )
            }

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            // ====================================================
            // EVIDENCIA - SEMANA 9
            // ====================================================

            Text(
                text =
                    "Evidencia",

                style =
                    MaterialTheme
                        .typography
                        .titleLarge
            )

            Text(
                text =
                    "La imagen se utilizará como evidencia de esta actividad. Puedes revisarla, reemplazarla o eliminarla antes de sincronizar."
            )

            mensajeDispositivo?.let { mensaje ->

                Text(
                    text =
                        mensaje,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium
                )
            }

            when (
                val estado =
                    evidenciaUiState
            ) {

                EvidenciaUiState.Cargando -> {

                    CircularProgressIndicator()
                }

                EvidenciaUiState.SinEvidencia -> {

                    Text(
                        text =
                            "Todavía no hay una imagen asociada a esta actividad."
                    )
                }

                is EvidenciaUiState.ConEvidencia -> {

                    val evidencia =
                        estado.evidencia

                    Text(
                        text =
                            "Estado: ${evidencia.estado.name}"
                    )

                    Text(
                        text =
                            "Archivo: ${evidencia.nombreArchivo}"
                    )

                    Text(
                        text =
                            "Tipo: ${evidencia.mimeType}"
                    )

                    Text(
                        text =
                            "Tamaño: ${
                                formatearTamano(
                                    evidencia.tamanoBytes
                                )
                            }"
                    )

                    // ============================================
                    // VISTA PREVIA
                    // ============================================

                    AndroidView(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    220.dp
                                ),

                        factory = {
                                androidContext ->

                            ImageView(
                                androidContext
                            ).apply {

                                scaleType =
                                    ImageView.ScaleType
                                        .CENTER_CROP
                            }
                        },

                        update = {
                                imageView ->

                            imageView.setImageURI(
                                Uri.parse(
                                    evidencia.uri
                                )
                            )
                        }
                    )

                    // ============================================
                    // ESTADO DE SINCRONIZACIÓN
                    // ============================================

                    when (
                        evidencia.estado
                    ) {

                        EstadoEvidencia.LOCAL -> {

                            Button(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),

                                onClick = {

                                    onSincronizarEvidencia(
                                        actividad.id
                                    )
                                }
                            ) {

                                Text(
                                    text =
                                        "Sincronizar evidencia"
                                )
                            }
                        }

                        EstadoEvidencia.SUBIENDO -> {

                            Button(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),

                                enabled =
                                    false,

                                onClick = {}
                            ) {

                                Text(
                                    text =
                                        "Sincronizando..."
                                )
                            }
                        }

                        EstadoEvidencia.SINCRONIZADA -> {

                            Text(
                                text =
                                    "Evidencia sincronizada correctamente."
                            )
                        }

                        EstadoEvidencia.FALLIDA -> {

                            Text(
                                text =
                                    "La sincronización falló. La evidencia local se conserva.",

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .error
                            )

                            Button(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),

                                onClick = {

                                    onReintentarSincronizacion(
                                        actividad.id
                                    )
                                }
                            ) {

                                Text(
                                    text =
                                        "Reintentar sincronización"
                                )
                            }
                        }
                    }

                    // ============================================
                    // REEMPLAZAR DESDE GALERÍA
                    // ============================================

                    OutlinedButton(
                        modifier =
                            Modifier
                                .fillMaxWidth(),

                        onClick = {

                            mensajeDispositivo =
                                null

                            selectorImagen.launch(
                                PickVisualMediaRequest(
                                    PickVisualMedia
                                        .ImageOnly
                                )
                            )
                        }
                    ) {

                        Text(
                            text =
                                "Reemplazar imagen"
                        )
                    }

                    // ============================================
                    // REEMPLAZAR CON CÁMARA
                    // ============================================

                    OutlinedButton(
                        modifier =
                            Modifier
                                .fillMaxWidth(),

                        onClick = {
                            tomarFoto()
                        }
                    ) {

                        Text(
                            text =
                                "Tomar nueva foto"
                        )
                    }

                    // ============================================
                    // ELIMINAR EVIDENCIA
                    // ============================================

                    OutlinedButton(
                        modifier =
                            Modifier
                                .fillMaxWidth(),

                        onClick = {

                            mensajeDispositivo =
                                null

                            onEliminarEvidencia(
                                actividad.id
                            )
                        }
                    ) {

                        Text(
                            text =
                                "Eliminar evidencia"
                        )
                    }
                }

                is EvidenciaUiState.Error -> {

                    Text(
                        text =
                            estado.mensaje,

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            }

            // ====================================================
            // SIN EVIDENCIA: SELECCIONAR O TOMAR FOTO
            // ====================================================

            if (
                evidenciaUiState
                        !is EvidenciaUiState.ConEvidencia
            ) {

                Button(
                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        mensajeDispositivo =
                            null

                        selectorImagen.launch(
                            PickVisualMediaRequest(
                                PickVisualMedia
                                    .ImageOnly
                            )
                        )
                    }
                ) {

                    Text(
                        text =
                            "Seleccionar imagen"
                    )
                }

                OutlinedButton(
                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {
                        tomarFoto()
                    }
                ) {

                    Text(
                        text =
                            "Tomar foto"
                    )
                }
            }

            // ====================================================
            // RESULTADO DE OPERACIONES
            // ====================================================

            when (
                val operacion =
                    operacionEvidenciaUiState
            ) {

                OperacionEvidenciaUiState.Inactiva -> {

                    // No mostramos mensaje.
                }

                OperacionEvidenciaUiState.EnCurso -> {

                    Text(
                        text =
                            "Procesando evidencia..."
                    )
                }

                is OperacionEvidenciaUiState.Exitosa -> {

                    Text(
                        text =
                            operacion.mensaje
                    )
                }

                is OperacionEvidenciaUiState.Fallida -> {

                    Text(
                        text =
                            operacion.mensaje,

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )

            // ====================================================
            // ELIMINAR ACTIVIDAD
            // ====================================================

            Button(
                modifier =
                    Modifier.fillMaxWidth(),

                onClick = {

                    onEliminar(
                        actividad.id
                    )
                }
            ) {

                Text(
                    text =
                        "Eliminar actividad"
                )
            }
        }

        // ========================================================
        // VOLVER
        // ========================================================

        OutlinedButton(
            modifier =
                Modifier.fillMaxWidth(),

            onClick =
                onBack
        ) {

            Text(
                text =
                    "Volver"
            )
        }
    }
}

// ================================================================
// ARCHIVO PARA CÁMARA
// ================================================================

private fun crearArchivoParaCamara(
    context: Context,
    actividadId: Long
): File {

    val directorio =
        File(
            context.filesDir,
            "evidencias"
        )

    if (
        !directorio.exists()
    ) {

        directorio.mkdirs()
    }

    return File(
        directorio,
        "evidencia_${actividadId}_${System.currentTimeMillis()}.jpg"
    )
}

// ================================================================
// TAMAÑO DE URI
// ================================================================

private fun obtenerTamanoUri(
    uri: Uri,
    context: Context
): Long {

    val resolver =
        context.contentResolver

    return try {

        resolver.query(
            uri,

            arrayOf(
                OpenableColumns.SIZE
            ),

            null,
            null,
            null
        )?.use { cursor ->

            if (
                !cursor.moveToFirst()
            ) {

                return@use -1L
            }

            val indice =
                cursor.getColumnIndex(
                    OpenableColumns.SIZE
                )

            if (
                indice < 0
            ) {

                -1L

            } else {

                cursor.getLong(
                    indice
                )
            }

        } ?: -1L

    } catch (
        _: Exception
    ) {

        -1L
    }
}

// ================================================================
// NOMBRE GENERADO PARA EVIDENCIA
// ================================================================

private fun generarNombreEvidencia(
    actividadId: Long,
    mimeType: String
): String {

    val extension =
        MimeTypeMap
            .getSingleton()
            .getExtensionFromMimeType(
                mimeType
            )
            ?: "img"

    return buildString {

        append(
            "evidencia_"
        )

        append(
            actividadId
        )

        append(
            "_"
        )

        append(
            System.currentTimeMillis()
        )

        append(
            "."
        )

        append(
            extension
        )
    }
}

// ================================================================
// FORMATEAR TAMAÑO
// ================================================================

private fun formatearTamano(
    bytes: Long
): String {

    if (
        bytes < 0L
    ) {

        return "Tamaño desconocido"
    }

    if (
        bytes < 1024L
    ) {

        return "$bytes B"
    }

    val kb =
        bytes / 1024.0

    if (
        kb < 1024.0
    ) {

        return String.format(
            "%.1f KB",
            kb
        )
    }

    val mb =
        kb / 1024.0

    return String.format(
        "%.2f MB",
        mb
    )
}