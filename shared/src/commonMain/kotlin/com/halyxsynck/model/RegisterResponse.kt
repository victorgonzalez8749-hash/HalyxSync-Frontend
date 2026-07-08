package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val mensaje: String
)