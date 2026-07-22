package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class CitaInfo(
    val id: Int,
    val pacienteNombre: String,
    val medico: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val motivo: String,
    val estado: String
)

@Serializable
data class AgendarCitaRequest(
    val correoPaciente: String,
    val correoDoctor: String,
    val medico: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val motivo: String
)