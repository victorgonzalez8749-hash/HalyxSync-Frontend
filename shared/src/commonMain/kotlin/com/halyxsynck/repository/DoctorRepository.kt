package com.halyxsynck.repository

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

    // NUEVO
    suspend fun obtenerEstadisticas(correoDoctor: String): EstadisticasDoctor {
        return api.obtenerEstadisticas(correoDoctor)
    }

}