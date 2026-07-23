package com.halyxsynck.api

import com.halyxsynck.model.EstudioInfo
import com.halyxsynck.model.SubirEstudioRequest
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class EstudioApi {

    private val baseUrl = "https://halyxsyncbackend-production.up.railway.app"

    suspend fun subirEstudio(request: SubirEstudioRequest): Boolean {

        return try {

            val respuesta = client.post("$baseUrl/estudios/subir") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            respuesta.status.value == 200

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    suspend fun obtenerEstudios(correo: String): List<EstudioInfo> {

        return try {
            client.get("$baseUrl/estudios/paciente?correo=$correo").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }

}