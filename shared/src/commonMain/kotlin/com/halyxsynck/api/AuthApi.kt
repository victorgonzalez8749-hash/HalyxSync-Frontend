package com.halyxsynck.api


class AuthApi {

    suspend fun login(
        correo: String,
        contrasena: String
    ): Boolean {

        // Aquí llamaremos al backend con Ktor
        return false

    }

    suspend fun registrar() {

        // Aquí enviaremos RegisterData al backend

    }

}