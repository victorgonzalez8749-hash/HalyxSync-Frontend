package com.halyxsynck.api

import com.halyxsynck.model.RegisterRequest
import com.halyxsynck.model.RegisterResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import com.halyxsynck.model.LoginRequest
import com.halyxsynck.model.LoginResponse

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
class AuthApi {

    private val baseUrl =
        "https://halyxsyncbackend-production.up.railway.app"

    suspend fun registrar(
        data: RegisterRequest
    ): RegisterResponse {

        return try {

            client.post("$baseUrl/auth/register") {

                contentType(ContentType.Application.Json)

                setBody(data)

            }.body()

        } catch (e: ClientRequestException) {

            RegisterResponse(
                success = false,
                mensaje = "Solicitud inválida"
            )

        } catch (e: ServerResponseException) {

            RegisterResponse(
                success = false,
                mensaje = "Error interno del servidor"
            )

        }

    }

    suspend fun login(
        data: LoginRequest
    ): LoginResponse {

        return client.post("$baseUrl/auth/login") {

            contentType(ContentType.Application.Json)

            setBody(data)

        }.body()

    }

}