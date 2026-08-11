package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class CambiarContrasenaRequest(
    val correo: String,
    val nuevaContrasena: String
)