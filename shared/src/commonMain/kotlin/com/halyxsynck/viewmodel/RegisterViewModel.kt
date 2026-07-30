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
        especialidad: String,
        edad: String,
        sexo: String,
        padecimientos: List<String>
    ): Boolean {

        val esDoctor = RegisterSession.rol == "DOCTOR"

        val respuesta = repository.registrar(

            RegisterRequest(

                nombre = nombre,

                apellidoPaterno = apellidoPaterno,

                apellidoMaterno = apellidoMaterno,

                correo = correo,

                telefono = telefono,

                contrasena = contrasena,

                rol = RegisterSession.rol,

                cedulaProfesional = if (esDoctor) cedula else null,

                especialidad = if (esDoctor) especialidad else null,

                edad = if (!esDoctor) edad.toIntOrNull() else null,

                sexo = if (!esDoctor) sexo else null,

                padecimientos = if (!esDoctor) padecimientos else null

            )

        )

        mensaje = respuesta.mensaje

        return respuesta.success

    }

    fun mostrarMensaje(texto: String) {

        mensaje = texto

    }

}