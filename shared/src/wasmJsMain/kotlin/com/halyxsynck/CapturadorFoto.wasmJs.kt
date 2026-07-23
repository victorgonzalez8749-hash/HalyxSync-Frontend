package com.halyxsynck

import androidx.compose.runtime.Composable

@Composable
actual fun rememberCapturadorFoto(onFoto: (ByteArray) -> Unit): () -> Unit {
    return { }
}