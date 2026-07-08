package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.theme.Background
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.session.RegisterSession

@Composable
fun RoleSelectionScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Crear cuenta",
            color = PrimaryBlue,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona el tipo de usuario"
        )

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(

            text = "👤 Soy Paciente",

            onClick = {

                RegisterSession.rol = "PACIENTE"

                Navigator.navigate(
                    Screen.RegistroPaciente
                )

            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(

            text = "🩺 Soy Doctor",

            onClick = {

                RegisterSession.rol = "DOCTOR"

                Navigator.navigate(
                    Screen.RegistroPaciente
                )

            }

        )

    }

}