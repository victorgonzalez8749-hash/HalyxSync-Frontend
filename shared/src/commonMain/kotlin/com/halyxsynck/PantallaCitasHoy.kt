package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.halyxsynck.api.CitaAgendaInfo
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*

@Composable
fun PantallaCitasHoy() {

    val repository = remember { DoctorRepository() }
    var citas by remember { mutableStateOf<List<CitaAgendaInfo>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        citas = repository.obtenerCitasHoy(UserSession.correo, FechaHoy.obtener())
        cargando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Citas de Hoy", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue, titleContentColor = White)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(Background).padding(padding).padding(16.dp)) {

            if (cargando) {
                Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }
            } else if (citas.isEmpty()) {
                Text("No tienes citas programadas para hoy.", color = TextSecondary)
            } else {
                citas.forEach { cita ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(46.dp).clip(CircleShape).background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(cita.hora, color = White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(cita.pacienteNombre, fontWeight = FontWeight.SemiBold, color = TextPrimary, modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                when (cita.estado) {
                                                    "Cancelada" -> Error.copy(alpha = 0.15f)
                                                    "Confirmada" -> Success.copy(alpha = 0.15f)
                                                    else -> PurpleAccent.copy(alpha = 0.15f)
                                                }
                                            )
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            cita.estado,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = when (cita.estado) {
                                                "Cancelada" -> Error
                                                "Confirmada" -> Success
                                                else -> PurpleAccent
                                            }
                                        )
                                    }
                                }
                                Text("${cita.edad} años", color = TextSecondary, fontSize = 12.sp)
                                Text(cita.motivo, color = if (cita.estado == "Cancelada") Error else PurpleAccent, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}