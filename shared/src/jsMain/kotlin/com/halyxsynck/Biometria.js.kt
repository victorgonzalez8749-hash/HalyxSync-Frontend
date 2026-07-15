package com.halyxsynck

actual object Biometria {
    actual fun disponible(): Boolean = false
    actual fun autenticar(onExito: () -> Unit, onError: (String) -> Unit) {
        onError("No disponible en esta plataforma")
    }
}