package com.sofia.miformacionctma.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
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

    onEvidenciaSeleccionada: (
        actividadId: Long,
        uri: String,
        mimeType: String,
        tamanoBytes: Long,
        nombreArchivo: String,
        archivoPropio: Boolean
    ) -> Unit = { _, _, _, _, _, _ -> },

    onEliminarEvidencia: (Long) -> Unit = {}
) {

    val context = LocalContext.current

    // =========================================================
    // FOTO PENDIENTE DE LA CÁMARA
    // =========================================================

    var fotoPendienteUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var fotoPendienteArchivo by remember {
        mutableStateOf<File?>(null)
    }

    // =========================================================
    // SELECTOR DE GALERÍA - PHOTO PICKER
    // =========================================================

    val selectorImagen =
        rememberLauncherForActivityResult(
            contract = PickVisualMedia()
        ) { uri ->

            // Si uri == null, el usuario canceló.
            // No se modifica la evidencia existente.
            if (uri != null && actividad != null) {

                val resolver =
                    context.contentResolver

                // Conservamos acceso a la URI para poder
                // recuperarla después de reiniciar la app.
                runCatching {
                    resolver.takePersistableUriPermission(
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
                        actividadId = actividad.id,
                        mimeType = mimeType
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

    // =========================================================
    // CÁMARA - TAKE PICTURE + FILEPROVIDER
    // =========================================================

    val camara =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { fotoTomada ->

            val uri =
                fotoPendienteUri

            val archivo =
                fotoPendienteArchivo

            if (
                fotoTomada &&
                actividad != null &&
                uri != null &&
                archivo != null &&
                archivo.exists()
            ) {

                onEvidenciaSeleccionada(
                    actividad.id,
                    uri.toString(),
                    "image/jpeg",
                    archivo.length(),
                    archivo.name,
                    true
                )

            } else {

                // Si el usuario canceló la cámara,
                // eliminamos el archivo vacío que habíamos preparado.
                archivo?.delete()
            }

            fotoPendienteUri = null
            fotoPendienteArchivo = null
        }

    // =========================================================
    // FUNCIÓN LOCAL PARA ABRIR LA CÁMARA
    // =========================================================

    fun tomarFoto() {

        if (actividad == null) {
            return
        }

        val resultado =
            crearArchivoParaCamara(
                context = context,
                actividadId = actividad.id
            )

        if (resultado != null) {

            fotoPendienteArchivo =
                resultado.first

            fotoPendienteUri =
                resultado.second

            camara.launch(
                resultado.second
            )
        }
    }

    // =========================================================
    // CONTENIDO
    // =========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp),
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        if (actividad == null) {

            Text(
                text = "Actividad no encontrada",
                style =
                    MaterialTheme.typography.headlineSmall
            )

            Text(
                text =
                    "El identificador solicitado no corresponde a una actividad disponible."
            )

        } else {

            Text(
                text = actividad.titulo,
                style =
                    MaterialTheme.typography.headlineSmall
            )

            actividad.descripcion
                ?.takeIf {
                    it.isNotBlank()
                }
                ?.let { descripcion ->

                    Text(
                        text = descripcion
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

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            // =====================================================
            // EVIDENCIA - SEMANA 9
            // =====================================================

            Text(
                text = "Evidencia",
                style =
                    MaterialTheme.typography.titleLarge
            )

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

                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),

                        factory = { androidContext ->

                            ImageView(
                                androidContext
                            ).apply {

                                scaleType =
                                    ImageView.ScaleType.CENTER_CROP
                            }
                        },

                        update = { imageView ->

                            imageView.setImageURI(
                                Uri.parse(
                                    evidencia.uri
                                )
                            )
                        }
                    )

                    // ---------------------------------------------
                    // REEMPLAZAR DESDE GALERÍA
                    // ---------------------------------------------

                    OutlinedButton(
                        modifier =
                            Modifier.fillMaxWidth(),

                        onClick = {

                            selectorImagen.launch(
                                PickVisualMediaRequest(
                                    PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    ) {

                        Text(
                            text =
                                "Reemplazar desde galería"
                        )
                    }

                    // ---------------------------------------------
                    // REEMPLAZAR USANDO CÁMARA
                    // ---------------------------------------------

                    OutlinedButton(
                        modifier =
                            Modifier.fillMaxWidth(),

                        onClick = {
                            tomarFoto()
                        }
                    ) {

                        Text(
                            text =
                                "Tomar nueva foto"
                        )
                    }

                    // ---------------------------------------------
                    // ELIMINAR
                    // ---------------------------------------------

                    OutlinedButton(
                        modifier =
                            Modifier.fillMaxWidth(),

                        onClick = {

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
                        text = estado.mensaje,
                        color =
                            MaterialTheme.colorScheme.error
                    )
                }
            }

            // =====================================================
            // SI NO HAY EVIDENCIA
            // =====================================================

            if (
                evidenciaUiState
                        !is EvidenciaUiState.ConEvidencia
            ) {

                Button(
                    modifier =
                        Modifier.fillMaxWidth(),

                    onClick = {

                        selectorImagen.launch(
                            PickVisualMediaRequest(
                                PickVisualMedia.ImageOnly
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

            // =====================================================
            // RESULTADO DE LA OPERACIÓN
            // =====================================================

            when (
                val operacion =
                    operacionEvidenciaUiState
            ) {

                OperacionEvidenciaUiState.Inactiva -> {
                    // No se muestra mensaje.
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
                            MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

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

        OutlinedButton(
            modifier =
                Modifier.fillMaxWidth(),

            onClick = onBack
        ) {

            Text(
                text = "Volver"
            )
        }
    }
}

// =============================================================
// CREAR ARCHIVO SEGURO PARA LA CÁMARA
// =============================================================

private fun crearArchivoParaCamara(
    context: Context,
    actividadId: Long
): Pair<File, Uri>? {

    return try {

        val directorio =
            File(
                context.filesDir,
                "evidencias"
            )

        if (!directorio.exists()) {
            directorio.mkdirs()
        }

        val nombreArchivo =
            "evidencia_" +
                    actividadId +
                    "_" +
                    System.currentTimeMillis() +
                    ".jpg"

        val archivo =
            File(
                directorio,
                nombreArchivo
            )

        if (!archivo.exists()) {
            archivo.createNewFile()
        }

        val uri =
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                archivo
            )

        Pair(
            archivo,
            uri
        )

    } catch (_: Exception) {

        null
    }
}

// =============================================================
// OBTENER TAMAÑO DE URI DEL PHOTO PICKER
// =============================================================

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

            if (!cursor.moveToFirst()) {
                return@use -1L
            }

            val indice =
                cursor.getColumnIndex(
                    OpenableColumns.SIZE
                )

            if (indice < 0) {
                -1L
            } else {
                cursor.getLong(
                    indice
                )
            }

        } ?: -1L

    } catch (_: Exception) {

        -1L
    }
}

// =============================================================
// GENERAR NOMBRE PARA IMAGEN DE GALERÍA
// =============================================================

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

    return "evidencia_" +
            actividadId +
            "_" +
            System.currentTimeMillis() +
            "." +
            extension
}

// =============================================================
// FORMATEAR TAMAÑO
// =============================================================

private fun formatearTamano(
    bytes: Long
): String {

    if (bytes < 1024L) {
        return "$bytes bytes"
    }

    val kb =
        bytes / 1024.0

    if (kb < 1024.0) {

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