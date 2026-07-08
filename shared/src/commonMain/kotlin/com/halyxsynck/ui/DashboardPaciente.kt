package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.theme.Background
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.session.UserSession

@Composable
fun DashboardPaciente() {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(

            text = "Bienvenid@ ${UserSession.nombre}",

            color = PrimaryBlue,

            fontSize = 30.sp

        )

        Spacer(modifier = Modifier.height(20.dp))

        Card {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    "Paciente",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Inicio de sesión exitoso")

            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        PrimaryButton(

            text = "Cerrar sesión",

            onClick = {

                UserSession.nombre = ""

                UserSession.rol = ""

                Navigator.navigate(Screen.Login)

            }

        )

    }

}