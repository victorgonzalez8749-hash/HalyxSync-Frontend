package com.halyxsynck.session

object UserSession {

    var nombre: String = ""

    var rol: String = ""

    var correo: String = ""

    var contrasenaGuardada: String = ""

    // NUEVO: guarda el correo específicamente para el login con huella,
    // y NO se borra al cerrar sesión
    var correoParaHuella: String = ""

}