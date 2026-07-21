package com.halyxsynck

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.theme.HalyxTheme
import com.halyxsynck.ui.LoginScreen
import com.halyxsynck.ui.SplashScreen
import com.halyxsynck.ui.RegisterScreen
import androidx.compose.material3.Text
import com.halyxsynck.ui.DashboardPaciente
import com.halyxsynck.ui.DashboardDoctor
import com.halyxsynck.ui.RoleSelectionScreen
import com.halyxsynck.ui.PantallaMisPacientes
import com.halyxsynck.ui.PantallaDetallePaciente
import com.halyxsynck.ui.PantallaPerfilDoctor

@Composable
@Preview
fun App() {

    HalyxTheme {

        when (val pantalla = Navigator.currentScreen) {

            Screen.Splash -> SplashScreen()

            Screen.Login -> LoginScreen()

            Screen.SeleccionRol -> RoleSelectionScreen()

            Screen.RegistroPaciente ->
                RegisterScreen()

            Screen.RegistroDoctor -> {
                // La crearemos después
            }

            Screen.DashboardPaciente -> DashboardPaciente()

            Screen.DashboardDoctor -> DashboardDoctor()

            Screen.DashboardAdmin -> {
                // La crearemos después
            }

            Screen.MisPacientes -> PantallaMisPacientes()

            // NUEVO
            Screen.PerfilDoctor -> PantallaPerfilDoctor()

            is Screen.DetallePaciente -> PantallaDetallePaciente(correo = pantalla.correo)

        }

    }

}