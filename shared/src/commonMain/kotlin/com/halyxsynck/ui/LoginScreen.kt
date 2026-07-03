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

@Composable
fun LoginScreen() {

    var correo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
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

            RoleSelector()

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryTextField(
                value = correo,
                onValueChange = {
                    correo = it
                },
                label = "Correo electrónico",
                placeholder = "nombre@ejemplo.com"
            )

            Spacer(modifier = Modifier.height(20.dp))

            PasswordField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ForgotPassword(
                onClick = {

                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Iniciar Sesión",
                onClick = {

                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            RegisterLink()

        }

    }

}