package com.halyxsynck

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
actual fun rememberCapturadorFoto(onFoto: (ByteArray) -> Unit): () -> Unit {

    val context = LocalContext.current

    var archivoTemporal by remember { mutableStateOf<File?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exito: Boolean ->

        if (exito && archivoTemporal != null) {

            val bitmapOriginal = BitmapFactory.decodeFile(archivoTemporal!!.absolutePath)

            if (bitmapOriginal != null) {

                val maxLado = 1600
                val bitmapFinal = if (bitmapOriginal.width > maxLado || bitmapOriginal.height > maxLado) {
                    val escala = maxLado.toFloat() / maxOf(bitmapOriginal.width, bitmapOriginal.height)
                    Bitmap.createScaledBitmap(
                        bitmapOriginal,
                        (bitmapOriginal.width * escala).toInt(),
                        (bitmapOriginal.height * escala).toInt(),
                        true
                    )
                } else {
                    bitmapOriginal
                }

                val stream = ByteArrayOutputStream()
                bitmapFinal.compress(Bitmap.CompressFormat.JPEG, 90, stream)
                onFoto(stream.toByteArray())

            }

            archivoTemporal?.delete()

        }

    }

    fun lanzarCamara() {
        val archivo = File.createTempFile("estudio_", ".jpg", context.cacheDir)
        val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
        archivoTemporal = archivo
        cameraLauncher.launch(uri)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            lanzarCamara()
        }
    }

    return {
        val permiso = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
        if (permiso == PackageManager.PERMISSION_GRANTED) {
            lanzarCamara()
        } else {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

}