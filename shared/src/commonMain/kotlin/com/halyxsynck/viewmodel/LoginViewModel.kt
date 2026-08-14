package com.halyxsynck.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.halyxsynck.TokenManager
import com.halyxsynck.api.NotificacionApi
import com.halyxsynck.repository.AuthRepository
import com.halyxsynck.session.UserSession

class LoginViewModel {

    private val repository = AuthRepository()
    private val notificacionApi = NotificacionApi()

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

            UserSession.cedulaProfesional = respuesta.cedulaProfesional ?: ""

            UserSession.correo = correo

            UserSession.contrasenaGuardada = contrasena

            UserSession.correoParaHuella = correo

            // NUEVO: espera activamente el token antes de mandarlo
            val token = TokenManager.obtenerToken()
            if (!token.isNullOrBlank()) {
                notificacionApi.registrarToken(correo, token)
            }

        }

        return respuesta.success

    }
}