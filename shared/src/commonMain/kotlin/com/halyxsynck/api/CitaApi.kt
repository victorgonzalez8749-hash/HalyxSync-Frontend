package com.halyxsynck.api

import com.halyxsynck.model.AgendarCitaRequest
import com.halyxsynck.model.CitaInfo
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CitaApi {

    private val baseUrl = "https://halyxsyncbackend-production.up.railway.app"

    suspend fun agendarCita(request: AgendarCitaRequest): Boolean {

        return try {

            val respuesta = client.post("$baseUrl/citas/agendar") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            respuesta.status.value == 200

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    suspend fun obtenerCitasPaciente(correo: String): List<CitaInfo> {

        return try {
            client.get("$baseUrl/citas/paciente?correo=$correo").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }

    suspend fun obtenerCitasDoctor(correo: String): List<CitaInfo> {

        return try {
            client.get("$baseUrl/citas/doctor?correo=$correo").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }

}