package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class EstudioInfo(
    val id: Int,
    val url: String,
    val descripcion: String,
    val fecha: String
)

@Serializable
data class SubirEstudioRequest(
    val correoPaciente: String,
    val imagenBase64: String,
    val descripcion: String,
    val fecha: String
)