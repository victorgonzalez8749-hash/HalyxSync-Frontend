package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class MedicamentoInfo(
    val nombre: String,
    val dosis: String,
    val horario: String,
    val padecimiento: String = "",
    val observaciones: String = ""
)

@Serializable
data class MedicoAsignadoInfo(
    val nombre: String,
    val correo: String,
    val especialidad: String,
    val padecimientos: List<String>
)

@Serializable
data class PacienteInfo(
    val nombreCompleto: String,
    val edad: Int,
    val sexo: String,
    val medicos: List<MedicoAsignadoInfo>,
    val medicamentos: List<MedicamentoInfo>
)