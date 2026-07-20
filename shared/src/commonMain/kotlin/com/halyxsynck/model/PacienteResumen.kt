package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class PacienteResumen(
    val correo: String,
    val nombreCompleto: String,
    val edad: Int,
    val sexo: String
)