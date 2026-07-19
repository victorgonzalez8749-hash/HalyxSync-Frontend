package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class MedicamentoRequest(
    val nombre: String,
    val dosis: String,
    val horario: String
)

@Serializable
data class RegistrarHistorialRequest(
    val correoPaciente: String,
    val correoDoctor: String,
    val edad: Int,
    val padecimientos: List<String>,
    val medicoAsignado: String,
    val especialidadMedico: String,
    val medicamentos: List<MedicamentoRequest>
)