package com.halyxsynck.repository

import com.halyxsynck.api.EstudioApi
import com.halyxsynck.model.EstudioInfo
import com.halyxsynck.model.SubirEstudioRequest

class EstudioRepository {

    private val api = EstudioApi()

    suspend fun subirEstudio(request: SubirEstudioRequest): Boolean {
        return api.subirEstudio(request)
    }

    suspend fun obtenerEstudios(correo: String): List<EstudioInfo> {
        return api.obtenerEstudios(correo)
    }

}