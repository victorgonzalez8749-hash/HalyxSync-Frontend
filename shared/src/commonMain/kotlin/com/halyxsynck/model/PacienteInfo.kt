package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class MedicamentoInfo(
    val nombre: String,
    val dosis: String,
    val horario: String
)

@Serializable
data class PacienteInfo(
    val nombreCompleto: String,
    val edad: Int,
    val padecimientos: List<String>,
    val medicoAsignado: String,
    val especialidadMedico: String,
    val medicamentos: List<MedicamentoInfo>
)