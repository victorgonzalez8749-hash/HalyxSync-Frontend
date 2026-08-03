package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class EstudioInfo(
    val id: Int,
    val url: String,
    val descripcion: String,
    val fecha: String,
    val doctorNombre: String = ""
)

@Serializable
data class SubirEstudioRequest(
    val correoPaciente: String,
    val correoDoctor: String,
    val imagenBase64: String,
    val descripcion: String,
    val fecha: String
)