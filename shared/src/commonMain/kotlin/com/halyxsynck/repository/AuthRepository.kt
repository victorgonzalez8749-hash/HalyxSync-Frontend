package com.halyxsynck.repository


class AuthRepository {

    suspend fun login(
        correo: String,
        contrasena: String
    ): Boolean {

        // Aquí llamaremos a AuthApi más adelante
        return false

    }

    suspend fun registrar() {

        // Aquí enviaremos RegisterData al servidor

    }

}