package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.FechaHoy
import com.halyxsynck.model.EnviarMensajeRequest
import com.halyxsynck.model.MensajeInfo
import com.halyxsynck.repository.MensajeRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch

@Composable
fun PantallaChat(correoOtro: String, nombreOtro: String) {

    val repository = remember { MensajeRepository() }
    val scope = rememberCoroutineScope()

    var mensajes by remember { mutableStateOf<List<MensajeInfo>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var textoNuevo by remember { mutableStateOf("") }
    var enviando by remember { mutableStateOf(false) }

    val esDoctor = UserSession.rol == "DOCTOR"

    suspend fun cargarMensajes() {
        mensajes = repository.obtenerConversacion(UserSession.correo, correoOtro)
    }

    LaunchedEffect(Unit) {
        cargarMensajes()
        cargando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text((if (esDoctor) "" else "Dr. ") + nombreOtro, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue, titleContentColor = White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textoNuevo,
                    onValueChange = { textoNuevo = it },
                    placeholder = { Text("Escribe un mensaje...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = TextSecondary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (textoNuevo.isNotBlank() && !enviando) {
                            val texto = textoNuevo
                            textoNuevo = ""
                            scope.launch {
                                enviando = true
                                repository.enviarMensaje(
                                    EnviarMensajeRequest(
                                        correoRemitente = UserSession.correo,
                                        correoDestinatario = correoOtro,
                                        texto = texto,
                                        fecha = FechaHoy.obtener(),
                                        hora = ""
                                    )
                                )
                                cargarMensajes()
                                enviando = false
                            }
                        }
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(PurpleAccent)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar", tint = White)
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            if (cargando) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }
            } else if (mensajes.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Todavía no hay mensajes. ¡Envía el primero!", color = TextSecondary, fontSize = 13.sp)
                }
            } else {
                Column(modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 12.dp)) {
                    mensajes.forEach { msg ->
                        BurbujaMensaje(msg)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }

    }

}

@Composable
private fun BurbujaMensaje(msg: MensajeInfo) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.esMio) Arrangement.End else Arrangement.Start
    ) {

        if (!msg.esMio) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = White, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (msg.esMio) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 260.dp)
        ) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (msg.esMio) 16.dp else 4.dp,
                            bottomEnd = if (msg.esMio) 4.dp else 16.dp
                        )
                    )
                    .background(if (msg.esMio) PurpleAccent else White)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    msg.texto,
                    color = if (msg.esMio) White else TextPrimary,
                    fontSize = 14.sp
                )
            }

            if (msg.hora.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(msg.hora, color = TextSecondary, fontSize = 10.sp)
            }

        }

    }

}
