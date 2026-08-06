package com.halyxsynck.repository

import com.halyxsynck.api.MensajeApi
import com.halyxsynck.model.ConversacionResumenInfo
import com.halyxsynck.model.EnviarMensajeRequest
import com.halyxsynck.model.MensajeInfo

class MensajeRepository {

    private val api = MensajeApi()

    suspend fun enviarMensaje(request: EnviarMensajeRequest): Boolean {
        return api.enviarMensaje(request)
    }

    suspend fun obtenerConversacion(correoUsuario: String, correoOtro: String): List<MensajeInfo> {
        return api.obtenerConversacion(correoUsuario, correoOtro)
    }

    suspend fun obtenerConversacionesPaciente(correo: String): List<ConversacionResumenInfo> {
        return api.obtenerConversacionesPaciente(correo)
    }

    suspend fun obtenerConversacionesDoctor(correo: String): List<ConversacionResumenInfo> {
        return api.obtenerConversacionesDoctor(correo)
    }

}
