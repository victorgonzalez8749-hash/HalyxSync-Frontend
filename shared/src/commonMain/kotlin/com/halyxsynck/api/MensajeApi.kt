package com.halyxsynck.api

import com.halyxsynck.model.ConversacionResumenInfo
import com.halyxsynck.model.EnviarMensajeRequest
import com.halyxsynck.model.MensajeInfo
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MensajeApi {

    private val baseUrl = "https://halyxsyncbackend-production.up.railway.app"

    suspend fun enviarMensaje(request: EnviarMensajeRequest): Boolean {
        return try {
            val respuesta = client.post("$baseUrl/mensajes/enviar") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            respuesta.status.value == 200
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun obtenerConversacion(correoUsuario: String, correoOtro: String): List<MensajeInfo> {
        return try {
            client.get("$baseUrl/mensajes/conversacion?correoUsuario=$correoUsuario&correoOtro=$correoOtro").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerConversacionesPaciente(correo: String): List<ConversacionResumenInfo> {
        return try {
            client.get("$baseUrl/mensajes/conversaciones-paciente?correo=$correo").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerConversacionesDoctor(correo: String): List<ConversacionResumenInfo> {
        return try {
            client.get("$baseUrl/mensajes/conversaciones-doctor?correo=$correo").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}
