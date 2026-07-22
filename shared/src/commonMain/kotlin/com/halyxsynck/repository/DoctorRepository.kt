package com.halyxsynck.repository

import com.halyxsynck.api.CitaAgendaInfo
import com.halyxsynck.api.DoctorApi
import com.halyxsynck.api.EstadisticasDoctor
import com.halyxsynck.model.PacienteResumen
import com.halyxsynck.model.RegistrarHistorialRequest

class DoctorRepository {

    private val api = DoctorApi()

    suspend fun registrarHistorial(request: RegistrarHistorialRequest): Boolean {
        return api.registrarHistorial(request)
    }

    suspend fun obtenerPacientes(correoDoctor: String): List<PacienteResumen> {
        return api.obtenerPacientes(correoDoctor)
    }

    suspend fun obtenerEstadisticas(correoDoctor: String, fechaHoy: String): EstadisticasDoctor {
        return api.obtenerEstadisticas(correoDoctor, fechaHoy)
    }

    // NUEVO
    suspend fun obtenerCitasHoy(correoDoctor: String, fechaHoy: String): List<CitaAgendaInfo> {
        return api.obtenerCitasHoy(correoDoctor, fechaHoy)
    }

}