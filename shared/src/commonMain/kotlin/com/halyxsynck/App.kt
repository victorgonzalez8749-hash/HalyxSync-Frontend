package com.halyxsynck

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.theme.HalyxTheme
import com.halyxsynck.ui.LoginScreen
import com.halyxsynck.ui.SplashScreen
import com.halyxsynck.ui.RegisterScreen


@Composable
@Preview
fun App() {

    HalyxTheme {

        when (Navigator.currentScreen) {

            Screen.Splash -> SplashScreen()

            Screen.Login -> LoginScreen()

            Screen.RegistroPaciente ->
                RegisterScreen()

            Screen.RegistroDoctor -> {
                // La crearemos después
            }

            Screen.DashboardPaciente -> {
                // La crearemos después
            }

            Screen.DashboardDoctor -> {
                // La crearemos después
            }

            Screen.DashboardAdmin -> {
                // La crearemos después
            }

        }

    }

}