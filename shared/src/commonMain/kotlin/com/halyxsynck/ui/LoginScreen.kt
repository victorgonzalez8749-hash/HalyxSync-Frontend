package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.halyxsynck.components.Logo
import com.halyxsynck.components.WelcomeHeader
import androidx.compose.runtime.LaunchedEffect
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
import com.halyxsynck.SesionSegura

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen() {

    val viewModel = LoginViewModel()
    val scope = rememberCoroutineScope()

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeHuella by remember { mutableStateOf("") }
    var mostrarConsentimiento by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val correoGuardado = SesionSegura.obtenerCorreo()
        val contrasenaGuardada = SesionSegura.obtenerContrasena()
        if (correoGuardado.isNotBlank() && contrasenaGuardada.isNotBlank()) {
            UserSession.correoParaHuella = correoGuardado
            UserSession.contrasenaGuardada = contrasenaGuardada
        }
    }

    fun intentarLoginConHuella() {
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
                            Navigator.navigateAndClear(Screen.DashboardDoctor)
                        } else {
                            Navigator.navigateAndClear(Screen.DashboardPaciente)
                        }
                    }
                }
            },
            onError = { error ->
                mensajeHuella = error
            }
        )
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
                Text(text = viewModel.mensaje, color = Color.Red)
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            ForgotPassword(onClick = {})

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Iniciar Sesión",
                onClick = {
                    scope.launch {
                        val correcto = viewModel.login()
                        if (correcto) {

                            SesionSegura.guardar(correo, contrasena)

                            if (UserSession.rol == "DOCTOR") {
                                Navigator.navigateAndClear(Screen.DashboardDoctor)
                            } else {
                                Navigator.navigateAndClear(Screen.DashboardPaciente)
                            }

                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón para iniciar sesión con huella o rostro
            PrimaryButton(
                text = "🔒 Iniciar sesión con huella o rostro",
                onClick = {

                    if (UserSession.correoParaHuella.isBlank() || UserSession.contrasenaGuardada.isBlank()) {
                        mensajeHuella = "Primero inicia sesión una vez con tu correo y contraseña"
                        return@PrimaryButton
                    }

                    if (!SesionSegura.tienePermisoBiometrico()) {
                        mostrarConsentimiento = true
                    } else {
                        intentarLoginConHuella()
                    }

                }
            )

            if (mensajeHuella.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensajeHuella, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(18.dp))

            RegisterLink()

        }

    }

    if (mostrarConsentimiento) {

        AlertDialog(
            onDismissRequest = { mostrarConsentimiento = false },
            title = { Text("Usar huella o reconocimiento facial") },
            text = {
                Text("Halyx Sync quiere usar el sensor de huella o reconocimiento facial de tu dispositivo para iniciar sesión de forma rápida y segura. ¿Autorizas su uso?")
            },
            confirmButton = {
                TextButton(onClick = {
                    SesionSegura.guardarPermisoBiometrico(true)
                    mostrarConsentimiento = false
                    intentarLoginConHuella()
                }) {
                    Text("Autorizar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarConsentimiento = false
                }) {
                    Text("No permitir")
                }
            }
        )

    }

}