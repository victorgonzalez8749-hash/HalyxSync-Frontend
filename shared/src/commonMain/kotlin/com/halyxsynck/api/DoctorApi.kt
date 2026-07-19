package com.halyxsynck.api

import com.halyxsynck.model.PacienteResumen
import com.halyxsynck.model.RegistrarHistorialRequest
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DoctorApi {

    private val baseUrl = "https://halyxsyncbackend-production.up.railway.app"

    suspend fun registrarHistorial(request: RegistrarHistorialRequest): Boolean {

        return try {

            val respuesta = client.post("$baseUrl/paciente/historial") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            respuesta.status.value == 200

        } catch (e: Exception) {

            e.printStackTrace()

            false

        }

    }

    // NUEVO: obtener la lista de pacientes asignados a este doctor
    suspend fun obtenerPacientes(correoDoctor: String): List<PacienteResumen> {

        return try {

            client.get("$baseUrl/doctor/pacientes?correo=$correoDoctor").body()

        } catch (e: Exception) {

            e.printStackTrace()

            emptyList()

        }

    }

}