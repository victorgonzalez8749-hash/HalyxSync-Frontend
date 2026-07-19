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

    // NUEVO: pantallas del menú lateral del doctor
    object MisPacientes : Screen()

    data class DetallePaciente(val correo: String) : Screen()

}