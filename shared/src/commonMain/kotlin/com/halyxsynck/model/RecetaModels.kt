package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class RecetaInfo(
    val pacienteCorreo: String,
    val pacienteNombre: String,
    val edad: Int,
    val medicamentos: List<MedicamentoInfo>
)