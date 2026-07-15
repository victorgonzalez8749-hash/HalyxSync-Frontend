package com.halyxsynck

expect object Biometria {
    fun disponible(): Boolean
    fun autenticar(onExito: () -> Unit, onError: (String) -> Unit)
}