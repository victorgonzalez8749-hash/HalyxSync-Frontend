package com.halyxsynck

actual object SesionSegura {
    actual fun guardar(correo: String, contrasena: String) {}
    actual fun obtenerCorreo(): String = ""
    actual fun obtenerContrasena(): String = ""
    actual fun limpiar() {}
    actual fun tienePermisoBiometrico(): Boolean = false
    actual fun guardarPermisoBiometrico(otorgado: Boolean) {}
}