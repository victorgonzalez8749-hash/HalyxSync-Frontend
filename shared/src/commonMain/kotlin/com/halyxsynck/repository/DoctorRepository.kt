package com.halyxsynck.repository

import com.halyxsynck.api.DoctorApi
import com.halyxsynck.model.PacienteResumen
import com.halyxsynck.model.RegistrarHistorialRequest

class DoctorRepository {

    private val api = DoctorApi()

    suspend fun registrarHistorial(request: RegistrarHistorialRequest): Boolean {
        return api.registrarHistorial(request)
    }

    // NUEVO
    suspend fun obtenerPacientes(correoDoctor: String): List<PacienteResumen> {
        return api.obtenerPacientes(correoDoctor)
    }

}