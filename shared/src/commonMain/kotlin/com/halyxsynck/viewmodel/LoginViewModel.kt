package com.halyxsynck.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginViewModel {

    var correo by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    var cargando by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf("")
        private set

    fun actualizarCorreo(valor: String) {
        correo = valor
    }

    fun actualizarContrasena(valor: String) {
        contrasena = valor
    }

}