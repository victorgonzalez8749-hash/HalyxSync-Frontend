package com.halyxsynck.api

import com.halyxsynck.model.AgregarMedicamentoRequest
import com.halyxsynck.model.PacienteInfo
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PacienteApi {

    private val baseUrl = "https://halyxsyncbackend-production.up.railway.app"

    suspend fun obtenerInfo(correo: String): PacienteInfo? {

        return try {

            client.get("$baseUrl/paciente/info?correo=$correo").body()

        } catch (e: Exception) {

            e.printStackTrace()

            null

        }

    }

    // NUEVO: agregar un medicamento sin borrar los existentes
    suspend fun agregarMedicamento(request: AgregarMedicamentoRequest): Boolean {

        return try {

            val respuesta = client.post("$baseUrl/paciente/medicamento") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            respuesta.status.value == 200

        } catch (e: Exception) {

            e.printStackTrace()

            false

        }

    }

}