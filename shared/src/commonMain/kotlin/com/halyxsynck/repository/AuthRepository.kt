package com.halyxsynck.repository

import com.halyxsynck.api.AuthApi
import com.halyxsynck.model.LoginRequest
import com.halyxsynck.model.LoginResponse

import com.halyxsynck.model.RegisterRequest
import com.halyxsynck.model.RegisterResponse

class AuthRepository {

    private val api = AuthApi()

    suspend fun login(
        correo: String,
        contrasena: String
    ): LoginResponse {

        return api.login(
            LoginRequest(
                correo = correo,
                contrasena = contrasena
            )
        )

    }

    suspend fun registrar(
        request: RegisterRequest
    ): RegisterResponse {

        return api.registrar(request)

    }

}