package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(

    val success: Boolean,

    val mensaje: String,

    val nombre: String? = null,

    val rol: String? = null

)