package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.ValidadorContrasena
import com.halyxsynck.components.PasswordField
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.PrimaryTextField
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.repository.AuthRepository
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch

@Composable
fun PantallaOlvideContrasena() {

    val repository = remember { AuthRepository() }
    val scope = rememberCoroutineScope()

    var correo by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var cambiando by remember { mutableStateOf(false) }
    var mostrarExito by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar contraseña", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue, titleContentColor = White)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(72.dp).clip(CircleShape).background(PurpleAccent.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LockReset, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(36.dp))
                }
            }

            Text(
                "Escribe tu correo y tu nueva contraseña. Se actualizará de inmediato.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            PrimaryTextField(
                value = correo,
                onValueChange = { correo = it },
                label = "Correo electrónico",
                placeholder = "nombre@ejemplo.com"
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = nuevaContrasena,
                onValueChange = { nuevaContrasena = it },
                label = "Nueva contraseña"
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(ValidadorContrasena.mensajeRequisitos(), color = TextSecondary, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = "Confirmar nueva contraseña"
            )

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensaje, color = Error, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = if (cambiando) "Actualizando..." else "Cambiar contraseña",
                onClick = {

                    if (cambiando) return@PrimaryButton

                    if (correo.isBlank()) {
                        mensaje = "Escribe tu correo"
                        return@PrimaryButton
                    }

                    if (!ValidadorContrasena.esSegura(nuevaContrasena)) {
                        mensaje = ValidadorContrasena.mensajeRequisitos()
                        return@PrimaryButton
                    }

                    if (nuevaContrasena != confirmarContrasena) {
                        mensaje = "Las contraseñas no coinciden"
                        return@PrimaryButton
                    }

                    scope.launch {
                        cambiando = true
                        val ok = repository.cambiarContrasena(correo, nuevaContrasena)
                        cambiando = false
                        if (ok) {
                            mensaje = ""
                            mostrarExito = true
                        } else {
                            mensaje = "No se encontró una cuenta con ese correo"
                        }
                    }

                }
            )

        }

    }

    if (mostrarExito) {
        AlertDialog(
            onDismissRequest = { },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Success, modifier = Modifier.size(40.dp)) },
            title = { Text("¡Contraseña actualizada!", fontWeight = FontWeight.Bold) },
            text = { Text("Ya puedes iniciar sesión con tu nueva contraseña.", color = TextSecondary, fontSize = 13.sp) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarExito = false
                    Navigator.navigateAndClear(Screen.Login)
                }) {
                    Text("Ir a iniciar sesión", color = PurpleAccent, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

}