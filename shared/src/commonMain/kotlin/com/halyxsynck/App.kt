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

@Composable
@Preview
fun App() {

    HalyxTheme {

        when (Navigator.currentScreen) {

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

        }

    }

}