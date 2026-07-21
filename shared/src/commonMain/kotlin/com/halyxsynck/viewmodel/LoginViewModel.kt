package com.halyxsynck.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.halyxsynck.repository.AuthRepository
import com.halyxsynck.session.UserSession

class LoginViewModel {

    private val repository = AuthRepository()

    var correo by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    fun actualizarCorreo(valor: String) {
        correo = valor
    }

    fun actualizarContrasena(valor: String) {
        contrasena = valor
    }

    suspend fun login(): Boolean {

        val respuesta = repository.login(
            correo,
            contrasena
        )

        mensaje = respuesta.mensaje

        if (respuesta.success) {

            UserSession.nombre = respuesta.nombre ?: ""

            UserSession.rol = respuesta.rol ?: ""

            UserSession.especialidad = respuesta.especialidad ?: ""

            UserSession.correo = correo

            UserSession.contrasenaGuardada = contrasena

            UserSession.correoParaHuella = correo // NUEVO: no se borra al cerrar sesión

        }

        return respuesta.success

    }
}