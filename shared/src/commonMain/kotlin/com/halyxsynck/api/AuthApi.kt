package com.halyxsynck.api

import com.halyxsynck.model.LoginRequest
import com.halyxsynck.model.LoginResponse
import com.halyxsynck.model.RegisterRequest
import com.halyxsynck.model.RegisterResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

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

            println("CLIENT ERROR: ${e.response.status}")

            RegisterResponse(
                success = false,
                mensaje = "Solicitud inválida"
            )

        } catch (e: ServerResponseException) {

            println("SERVER ERROR: ${e.response.status}")

            RegisterResponse(
                success = false,
                mensaje = "Error interno del servidor"
            )

        } catch (e: Exception) {

            e.printStackTrace()

            RegisterResponse(
                success = false,
                mensaje = "No se pudo conectar con el servidor"
            )

        }

    }

    suspend fun login(
        data: LoginRequest
    ): LoginResponse {

        return try {

            client.post("$baseUrl/auth/login") {

                contentType(ContentType.Application.Json)

                setBody(data)

            }.body()

        } catch (e: Exception) {

            e.printStackTrace()

            LoginResponse(
                success = false,
                mensaje = "Error al iniciar sesión"
            )

        }

    }

}