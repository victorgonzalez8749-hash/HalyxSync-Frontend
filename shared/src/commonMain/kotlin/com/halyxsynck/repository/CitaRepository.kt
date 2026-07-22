package com.halyxsynck.repository

import com.halyxsynck.api.CitaApi
import com.halyxsynck.model.AgendarCitaRequest
import com.halyxsynck.model.CitaInfo

class CitaRepository {

    private val api = CitaApi()

    suspend fun agendarCita(request: AgendarCitaRequest): Boolean {
        return api.agendarCita(request)
    }

    suspend fun obtenerCitasPaciente(correo: String): List<CitaInfo> {
        return api.obtenerCitasPaciente(correo)
    }

    suspend fun obtenerCitasDoctor(correo: String): List<CitaInfo> {
        return api.obtenerCitasDoctor(correo)
    }

}