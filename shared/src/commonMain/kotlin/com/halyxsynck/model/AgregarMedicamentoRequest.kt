package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class MedicamentoCompleto(
    val nombre: String,
    val dosis: String,
    val horario: String,
    val padecimiento: String = "",
    val observaciones: String = ""
)

@Serializable
data class AgregarMedicamentoRequest(
    val correoPaciente: String,
    val medicamento: MedicamentoCompleto
)