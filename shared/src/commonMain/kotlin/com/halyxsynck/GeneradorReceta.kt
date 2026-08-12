package com.halyxsynck

import androidx.compose.runtime.Composable
import com.halyxsynck.model.RecetaInfo

expect class GeneradorReceta {
    fun generarYCompartir(receta: RecetaInfo, nombreDoctor: String, especialidad: String)
}

@Composable
expect fun rememberGeneradorReceta(): GeneradorReceta