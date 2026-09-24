package com.sofia.miformacionctma.device

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.util.UUID

fun nuevaEvidenciaUri(context: Context): Uri {
    val dir = File(context.filesDir, "evidencias").apply { mkdirs() }
    val file = File(dir, "evidencia_${UUID.randomUUID()}.jpg").apply { createNewFile() }
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}
