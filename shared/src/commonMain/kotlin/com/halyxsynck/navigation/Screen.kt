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

    data class DetallePaciente(val correo: String) : Screen()

}