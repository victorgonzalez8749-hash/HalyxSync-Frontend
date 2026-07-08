package com.halyxsynck.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.halyxsynck.model.RegisterRequest
import com.halyxsynck.repository.AuthRepository
import com.halyxsynck.session.RegisterSession

class RegisterViewModel {

    private val repository = AuthRepository()

    var mensaje by mutableStateOf("")
        private set

    suspend fun registrar(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        correo: String,
        telefono: String,
        contrasena: String,
        cedula: String,
        especialidad: String
    ): Boolean {

        val respuesta = repository.registrar(

            RegisterRequest(

                nombre = nombre,

                apellidoPaterno = apellidoPaterno,

                apellidoMaterno = apellidoMaterno,

                correo = correo,

                telefono = telefono,

                contrasena = contrasena,

                rol = RegisterSession.rol,

                cedulaProfesional =
                    if (RegisterSession.rol == "DOCTOR")
                        cedula
                    else
                        null,

                especialidad =
                    if (RegisterSession.rol == "DOCTOR")
                        especialidad
                    else
                        null

            )

        )

        mensaje = respuesta.mensaje

        return respuesta.success

    }

    fun mostrarMensaje(texto: String) {

        mensaje = texto

    }

}