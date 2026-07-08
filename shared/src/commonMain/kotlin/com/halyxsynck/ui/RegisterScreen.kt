package com.halyxsynck.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.theme.Background
import com.halyxsynck.theme.PrimaryBlue

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.RegisterForm
import com.halyxsynck.model.RegisterData
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.session.RegisterSession
import com.halyxsynck.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen() {

    val viewModel = RegisterViewModel()

    val scope = rememberCoroutineScope()

    var data by remember {

        mutableStateOf(

            RegisterData(
                rol = RegisterSession.rol
            )

        )

    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(

            text = if (RegisterSession.rol == "DOCTOR")
                "Registro de Doctor"
            else
                "Registro de Paciente",

            color = PrimaryBlue,

            fontSize = 28.sp

        )

        Spacer(modifier = Modifier.height(24.dp))


        RegisterForm(

            data = data,

            onNombreChange = {
                data = data.copy(nombre = it)
            },

            onApellidoPaternoChange = {
                data = data.copy(apellidoPaterno = it)
            },

            onApellidoMaternoChange = {
                data = data.copy(apellidoMaterno = it)
            },

            onCorreoChange = {
                data = data.copy(correo = it)
            },

            onTelefonoChange = {
                data = data.copy(telefono = it)
            },

            onPasswordChange = {
                data = data.copy(contrasena = it)
            },

            onConfirmPasswordChange = {
                data = data.copy(confirmarContrasena = it)
            },

            onCedulaChange = {
                data = data.copy(cedulaProfesional = it)
            },

            onEspecialidadChange = {
                data = data.copy(especialidad = it)
            }

        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(

            text = "Registrarme",

            onClick = {

                scope.launch {

                    if (data.contrasena != data.confirmarContrasena) {

                        viewModel.mostrarMensaje(
                            "Las contraseñas no coinciden"
                        )

                        return@launch

                    }

                    val registrado = viewModel.registrar(

                        nombre = data.nombre,

                        apellidoPaterno = data.apellidoPaterno,

                        apellidoMaterno = data.apellidoMaterno,

                        correo = data.correo,

                        telefono = data.telefono,

                        contrasena = data.contrasena,

                        cedula = data.cedulaProfesional,

                        especialidad = data.especialidad

                    )

                    if (registrado) {

                        Navigator.navigate(Screen.Login)

                    }

                }

            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.mensaje.isNotBlank()) {

            Text(

                text = viewModel.mensaje,

                color = if (viewModel.mensaje.contains("correctamente"))
                    Color(0xFF2E7D32)
                else
                    Color.Red

            )
        }
    }
}