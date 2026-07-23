package com.halyxsynck

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCapturadorFoto(onFoto: (ByteArray) -> Unit): () -> Unit