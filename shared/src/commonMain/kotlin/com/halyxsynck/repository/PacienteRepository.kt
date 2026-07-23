package com.halyxsynck.repository

import com.halyxsynck.api.PacienteApi
import com.halyxsynck.model.AgregarMedicamentoRequest
import com.halyxsynck.model.PacienteInfo

class PacienteRepository {

    private val api = PacienteApi()

    suspend fun obtenerInfo(correo: String): PacienteInfo? {
        return api.obtenerInfo(correo)
    }

    // NUEVO
    suspend fun agregarMedicamento(request: AgregarMedicamentoRequest): Boolean {
        return api.agregarMedicamento(request)
    }

}