package com.halyxsynck

actual object TokenManager {
    actual var tokenPendiente: String? = null
    actual suspend fun obtenerToken(): String? = null
}