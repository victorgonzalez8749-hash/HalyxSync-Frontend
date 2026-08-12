package com.halyxsynck

import androidx.compose.runtime.Composable
import com.halyxsynck.model.RecetaInfo

actual class GeneradorReceta {
    actual fun generarYCompartir(receta: RecetaInfo, nombreDoctor: String, especialidad: String) {
        // No implementado en esta plataforma
    }
}

@Composable
actual fun rememberGeneradorReceta(): GeneradorReceta = GeneradorReceta()