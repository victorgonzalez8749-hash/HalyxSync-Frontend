package com.halyxsynck.model

import kotlinx.serialization.Serializable

@Serializable
data class MensajeInfo(
    val id: Int,
    val remitenteCorreo: String,
    val remitenteNombre: String,
    val texto: String,
    val fecha: String,
    val hora: String,
    val esMio: Boolean
)

@Serializable
data class EnviarMensajeRequest(
    val correoRemitente: String,
    val correoDestinatario: String,
    val texto: String,
    val fecha: String,
    val hora: String
)

@Serializable
data class ConversacionResumenInfo(
    val correo: String,
    val nombre: String,
    val ultimoMensaje: String,
    val fecha: String,
    val hora: String,
    val noLeidos: Int
)
