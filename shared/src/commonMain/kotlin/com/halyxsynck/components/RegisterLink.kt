package com.halyxsynck.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.theme.PrimaryBlue

@Composable
fun RegisterLink() {

    Row {

        Text(
            text = "¿No tienes cuenta? ",
            color = Color.Gray
        )

        Text(
            text = "Crear cuenta",
            color = PrimaryBlue,
            modifier = Modifier.clickable {

                Navigator.navigate(
                    Screen.RegistroPaciente
                )

            }
        )

    }

}