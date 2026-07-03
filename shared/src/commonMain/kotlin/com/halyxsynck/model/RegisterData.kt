package com.halyxsynck.model


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

    val especialidad: String = ""

)