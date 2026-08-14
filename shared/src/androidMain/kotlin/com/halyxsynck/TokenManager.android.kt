package com.halyxsynck

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual object TokenManager {

    actual var tokenPendiente: String? = null

    actual suspend fun obtenerToken(): String? {

        if (!tokenPendiente.isNullOrBlank()) return tokenPendiente

        return suspendCancellableCoroutine { continuacion ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { tarea ->
                val token = if (tarea.isSuccessful) tarea.result else null
                tokenPendiente = token
                if (continuacion.isActive) continuacion.resume(token)
            }
        }

    }

}