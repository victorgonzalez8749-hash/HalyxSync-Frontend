package com.halyxsynck

actual object SesionSegura {

    private fun prefs() = Biometria.activityRef?.get()
        ?.getSharedPreferences("halyx_sesion", android.content.Context.MODE_PRIVATE)

    actual fun guardar(correo: String, contrasena: String) {
        prefs()?.edit()
            ?.putString("correo", correo)
            ?.putString("contrasena", contrasena)
            ?.apply()
    }

    actual fun obtenerCorreo(): String {
        return prefs()?.getString("correo", "") ?: ""
    }

    actual fun obtenerContrasena(): String {
        return prefs()?.getString("contrasena", "") ?: ""
    }

    actual fun limpiar() {
        prefs()?.edit()?.clear()?.apply()
    }

    actual fun tienePermisoBiometrico(): Boolean {
        return prefs()?.getBoolean("permiso_biometrico", false) ?: false
    }

    actual fun guardarPermisoBiometrico(otorgado: Boolean) {
        prefs()?.edit()?.putBoolean("permiso_biometrico", otorgado)?.apply()
    }

}