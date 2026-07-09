package com.halyxsynck.api

import com.halyxsynck.model.PacienteInfo
import io.ktor.client.call.body
import io.ktor.client.request.get

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

}