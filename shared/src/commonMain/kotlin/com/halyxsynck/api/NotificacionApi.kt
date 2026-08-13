package com.halyxsynck.api

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class RegistrarTokenRequest(val correo: String, val token: String)

class NotificacionApi {

    private val baseUrl = "https://halyxsyncbackend-production.up.railway.app"

    suspend fun registrarToken(correo: String, token: String): Boolean {

        return try {
            val respuesta = client.post("$baseUrl/notificaciones/registrar-token") {
                contentType(ContentType.Application.Json)
                setBody(RegistrarTokenRequest(correo, token))
            }
            respuesta.status.value == 200
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

}