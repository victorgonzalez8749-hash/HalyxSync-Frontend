package com.halyxsynck

expect object SesionSegura {
    fun guardar(correo: String, contrasena: String)
    fun obtenerCorreo(): String
    fun obtenerContrasena(): String
    fun limpiar()
    fun tienePermisoBiometrico(): Boolean
    fun guardarPermisoBiometrico(otorgado: Boolean)
}