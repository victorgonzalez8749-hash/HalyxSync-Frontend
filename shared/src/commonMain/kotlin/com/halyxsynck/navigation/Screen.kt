package com.halyxsynck.navigation


sealed class Screen {

    object Splash : Screen()

    object Login : Screen()

    object SeleccionRol : Screen()

    object RegistroPaciente : Screen()

    object RegistroDoctor : Screen()

    object DashboardPaciente : Screen()

    object DashboardDoctor : Screen()

    object DashboardAdmin : Screen()

    object MisPacientes : Screen()

    object PerfilDoctor : Screen()

    object Consultas : Screen()

    object CitasHoy : Screen()

    object Mensajes : Screen()

    object Recetas : Screen()

    object OlvideContrasena : Screen()

    data class DetallePaciente(val correo: String) : Screen()

    data class Chat(val correoOtro: String, val nombreOtro: String) : Screen()

}