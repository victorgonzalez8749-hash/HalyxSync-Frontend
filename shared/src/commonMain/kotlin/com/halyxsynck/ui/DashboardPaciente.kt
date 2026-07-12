package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.components.PatientAvatar
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.model.PacienteInfo
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.repository.PacienteRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*

@Composable
fun DashboardPaciente() {

    val repository = remember { PacienteRepository() }

    var info by remember { mutableStateOf<PacienteInfo?>(null) }

    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        info = repository.obtenerInfo(UserSession.correo)
        cargando = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Avatar dibujado con formas (sin descargar nada)
            PatientAvatar()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = info?.nombreCompleto ?: UserSession.nombre,
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Paciente",
                color = White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            if (cargando) {

                Box(
                    modifier = Modifier.fillMaxWidth().padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else if (info == null) {

                InfoCard(titulo = "Sin información") {
                    Text("Todavía no hay historial médico registrado.", color = TextSecondary)
                }

            } else {

                InfoCard(titulo = "🩺 Médico asignado") {
                    Text(info!!.medicoAsignado, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Text(info!!.especialidadMedico, color = TextSecondary, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                InfoCard(titulo = "🎂 Edad") {
                    Text("${info!!.edad} años", color = TextPrimary)
                }

                Spacer(modifier = Modifier.height(14.dp))

                InfoCard(titulo = "📋 Padecimientos") {
                    info!!.padecimientos.forEach { p ->
                        Text("• $p", color = TextPrimary, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                InfoCard(titulo = "💊 Medicamentos recetados") {
                    info!!.medicamentos.forEach { med ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Text("💊 ${med.nombre}", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Text(med.dosis, color = TextSecondary, fontSize = 13.sp)
                            Text("⏰ ${med.horario}", color = PurpleAccent, fontSize = 13.sp)
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            PrimaryButton(
                text = "Cerrar sesión",
                onClick = {
                    UserSession.nombre = ""
                    UserSession.rol = ""
                    UserSession.correo = ""
                    Navigator.navigate(Screen.Login)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

    }

}

@Composable
private fun InfoCard(
    titulo: String,
    contenido: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {

        Column(modifier = Modifier.padding(18.dp)) {

            Text(
                text = titulo,
                color = PurpleAccent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            contenido()

        }

    }

}