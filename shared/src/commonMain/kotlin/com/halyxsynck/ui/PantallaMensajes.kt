package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.model.ConversacionResumenInfo
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.repository.MensajeRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*

@Composable
fun PantallaMensajes() {

    val repository = remember { MensajeRepository() }
    var conversaciones by remember { mutableStateOf<List<ConversacionResumenInfo>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    val esDoctor = UserSession.rol == "DOCTOR"

    LaunchedEffect(Unit) {
        conversaciones = if (esDoctor) {
            repository.obtenerConversacionesDoctor(UserSession.correo)
        } else {
            repository.obtenerConversacionesPaciente(UserSession.correo)
        }
        cargando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mensajes", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue, titleContentColor = White)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(16.dp)
        ) {

            if (cargando) {

                Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else if (conversaciones.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(80.dp).clip(CircleShape).background(PurpleAccent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.MailOutline, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(38.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        if (esDoctor) "Todavía no tienes conversaciones con pacientes" else "Todavía no tienes conversaciones con tus médicos",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

            } else {

                conversaciones.forEach { conv ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable {
                                Navigator.navigate(Screen.Chat(correoOtro = conv.correo, nombreOtro = conv.nombre))
                            },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = White, modifier = Modifier.size(24.dp))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    (if (esDoctor) "" else "Dr. ") + conv.nombre,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    fontSize = 15.sp
                                )
                                Text(
                                    conv.ultimoMensaje,
                                    color = TextSecondary,
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                if (conv.hora.isNotBlank()) {
                                    Text(conv.hora, color = TextSecondary, fontSize = 11.sp)
                                }
                                if (conv.noLeidos > 0) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(PurpleAccent)
                                            .padding(horizontal = 7.dp, vertical = 2.dp)
                                    ) {
                                        Text(conv.noLeidos.toString(), color = White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                        }
                    }

                }

            }

        }

    }

}
