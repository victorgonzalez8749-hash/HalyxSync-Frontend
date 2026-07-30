package com.halyxsynck.model
import kotlinx.serialization.Serializable


@Serializable
data class RegisterData(

    val nombre: String = "",

    val apellidoPaterno: String = "",

    val apellidoMaterno: String = "",

    val correo: String = "",

    val telefono: String = "",

    val contrasena: String = "",

    val confirmarContrasena: String = "",

    val rol: String = "PACIENTE",

    val cedulaProfesional: String = "",

    val especialidad: String = "",

    // NUEVO: solo aplica cuando rol == "PACIENTE"
    val edad: String = "",

    val sexo: String = "Masculino",

    val padecimientos: List<String> = emptyList()

)