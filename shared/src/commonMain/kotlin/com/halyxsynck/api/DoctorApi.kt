package com.halyxsynck.api

import com.halyxsynck.model.PacienteResumen
import com.halyxsynck.model.RegistrarHistorialRequest
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

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

    suspend fun obtenerPacientes(correoDoctor: String): List<PacienteResumen> {

        return try {

            client.get("$baseUrl/doctor/pacientes?correo=$correoDoctor").body()

        } catch (e: Exception) {

            e.printStackTrace()

            emptyList()

        }

    }

    suspend fun obtenerEstadisticas(correoDoctor: String, fechaHoy: String): EstadisticasDoctor {

        return try {

            client.get("$baseUrl/doctor/estadisticas?correo=$correoDoctor&fecha=$fechaHoy").body()

        } catch (e: Exception) {

            e.printStackTrace()

            EstadisticasDoctor(totalPacientes = 0, citasHoy = 0)

        }

    }

    // NUEVO: agenda de citas de hoy con detalle
    suspend fun obtenerCitasHoy(correoDoctor: String, fechaHoy: String): List<CitaAgendaInfo> {

        return try {

            client.get("$baseUrl/doctor/citas-hoy?correo=$correoDoctor&fecha=$fechaHoy").body()

        } catch (e: Exception) {

            e.printStackTrace()

            emptyList()

        }

    }

}

@Serializable
data class EstadisticasDoctor(
    val totalPacientes: Int,
    val citasHoy: Int = 0
)

@Serializable
data class CitaAgendaInfo(
    val pacienteNombre: String,
    val edad: Int,
    val hora: String,
    val motivo: String
)