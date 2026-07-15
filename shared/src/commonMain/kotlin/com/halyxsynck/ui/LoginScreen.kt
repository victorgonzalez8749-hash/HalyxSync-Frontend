package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.halyxsynck.components.Logo
import com.halyxsynck.components.WelcomeHeader
import com.halyxsynck.components.RoleSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.halyxsynck.components.PrimaryTextField
import com.halyxsynck.components.LoginCard
import com.halyxsynck.components.PasswordField
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.ForgotPassword
import com.halyxsynck.components.RegisterLink

import com.halyxsynck.theme.Background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.halyxsynck.viewmodel.LoginViewModel

import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.session.UserSession
import com.halyxsynck.Biometria

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen() {

    val viewModel = LoginViewModel()
    val scope = rememberCoroutineScope()


    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var mensajeHuella by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Logo()

        Spacer(modifier = Modifier.height(24.dp))

        WelcomeHeader()

        Spacer(modifier = Modifier.height(32.dp))

        LoginCard {

            PrimaryTextField(
                value = correo,
                onValueChange = {

                    correo = it

                    viewModel.actualizarCorreo(it)

                },
                label = "Correo electrónico",
                placeholder = "nombre@ejemplo.com"
            )

            Spacer(modifier = Modifier.height(20.dp))

            PasswordField(
                value = contrasena,
                onValueChange = {

                    contrasena = it

                    viewModel.actualizarContrasena(it)

                }
            )

            if (viewModel.mensaje.isNotBlank()) {

                Text(
                    text = viewModel.mensaje,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.height(12.dp))

            }
            Spacer(modifier = Modifier.height(12.dp))

            ForgotPassword(
                onClick = {

                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(

                text = "Iniciar Sesión",

                onClick = {

                    scope.launch {

                        val correcto = viewModel.login()

                        if (correcto) {

                            if (UserSession.rol == "DOCTOR") {
                                Navigator.navigate(Screen.DashboardDoctor)
                            } else {
                                Navigator.navigate(Screen.DashboardPaciente)
                            }

                        }

                    }

                }

            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para iniciar sesión con huella
            PrimaryButton(

                text = "🔒 Iniciar sesión con huella",

                onClick = {

                    if (UserSession.correoParaHuella.isBlank() || UserSession.contrasenaGuardada.isBlank()) {

                        mensajeHuella = "Primero inicia sesión una vez con tu correo y contraseña"

                    } else {

                        Biometria.autenticar(
                            onExito = {

                                correo = UserSession.correoParaHuella
                                viewModel.actualizarCorreo(UserSession.correoParaHuella)
                                contrasena = UserSession.contrasenaGuardada
                                viewModel.actualizarContrasena(UserSession.contrasenaGuardada)

                                scope.launch {

                                    val correcto = viewModel.login()

                                    if (correcto) {

                                        if (UserSession.rol == "DOCTOR") {
                                            Navigator.navigate(Screen.DashboardDoctor)
                                        } else {
                                            Navigator.navigate(Screen.DashboardPaciente)
                                        }

                                    }

                                }

                            },
                            onError = { error ->
                                mensajeHuella = error
                            }
                        )

                    }

                }

            )

            if (mensajeHuella.isNotBlank()) {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = mensajeHuella,
                    color = Color.Red
                )

            }

            Spacer(modifier = Modifier.height(18.dp))

            RegisterLink()

        }

    }



}