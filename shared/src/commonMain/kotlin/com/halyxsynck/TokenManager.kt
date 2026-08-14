package com.halyxsynck

expect object TokenManager {
    var tokenPendiente: String?
    suspend fun obtenerToken(): String?
}