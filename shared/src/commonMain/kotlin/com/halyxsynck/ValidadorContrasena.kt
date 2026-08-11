package com.halyxsynck

object ValidadorContrasena {

    fun esSegura(contrasena: String): Boolean {
        if (contrasena.length < 8) return false
        if (!contrasena.any { it.isUpperCase() }) return false
        if (!contrasena.any { it.isDigit() }) return false
        return true
    }

    fun mensajeRequisitos(): String {
        return "Debe tener al menos 8 caracteres, una mayúscula y un número"
    }

}