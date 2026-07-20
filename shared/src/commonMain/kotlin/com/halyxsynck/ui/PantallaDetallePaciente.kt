package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.PrimaryTextField
import com.halyxsynck.model.MedicamentoRequest
import com.halyxsynck.model.RegistrarHistorialRequest
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch

@Composable
fun PantallaDetallePaciente(correo: String) {

    val repository = remember { DoctorRepository() }
    val scope = rememberCoroutineScope()

    var edad by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Masculino") }
    var padecimientos by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }

    var medNombre by remember { mutableStateOf("") }
    var medDosis by remember { mutableStateOf("") }
    var medHorario by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Paciente", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // Cabecera con avatar tipo iniciales
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Paciente", fontSize = 12.sp, color = TextSecondary)
                    Text(correo, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {

                    TituloConIcono(icono = Icons.Default.Person, texto = "Datos del paciente", color = PurpleAccent)

                    Spacer(modifier = Modifier.height(12.dp))

                    PrimaryTextField(
                        value = edad,
                        onValueChange = { edad = it },
                        label = "Edad",
                        placeholder = "Ej. 28"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Sexo", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        listOf("Masculino", "Femenino", "Otro").forEach { opcion ->
                            FilterChip(
                                selected = sexo == opcion,
                                onClick = { sexo = opcion },
                                label = { Text(opcion, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PurpleAccent,
                                    selectedLabelColor = White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    PrimaryTextField(
                        value = padecimientos,
                        onValueChange = { padecimientos = it },
                        label = "Padecimientos (separados por coma)",
                        placeholder = "Diabetes, Hipertensión"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PrimaryTextField(
                        value = especialidad,
                        onValueChange = { especialidad = it },
                        label = "Tu especialidad",
                        placeholder = "Ej. Medicina Interna"
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {

                    TituloConIcono(icono = Icons.Default.Medication, texto = "Receta médica", color = Success)

                    Spacer(modifier = Modifier.height(12.dp))

                    PrimaryTextField(
                        value = medNombre,
                        onValueChange = { medNombre = it },
                        label = "Nombre del medicamento",
                        placeholder = "Ej. Metformina"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PrimaryTextField(
                        value = medDosis,
                        onValueChange = { medDosis = it },
                        label = "Dosis",
                        placeholder = "Ej. 850mg"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Horario de toma", fontSize = 12.sp, color = TextSecondary)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    PrimaryTextField(
                        value = medHorario,
                        onValueChange = { medHorario = it },
                        label = "Horario",
                        placeholder = "Ej. 08:00,14:00,20:00"
                    )

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = "Guardar historial",
                onClick = {
                    scope.launch {

                        val request = RegistrarHistorialRequest(
                            correoPaciente = correo,
                            correoDoctor = UserSession.correo,
                            edad = edad.toIntOrNull() ?: 0,
                            sexo = sexo,
                            padecimientos = padecimientos.split(",").map { it.trim() },
                            medicoAsignado = UserSession.nombre,
                            especialidadMedico = especialidad,
                            medicamentos = listOf(MedicamentoRequest(medNombre, medDosis, medHorario))
                        )

                        val guardado = repository.registrarHistorial(request)
                        mensaje = if (guardado) "Historial guardado correctamente" else "Error al guardar"

                    }
                }
            )

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensaje, color = if (mensaje.contains("correctamente")) Success else Error)
            }

            Spacer(modifier = Modifier.height(24.dp))

        }

    }

}

@Composable
private fun TituloConIcono(icono: androidx.compose.ui.graphics.vector.ImageVector, texto: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(texto, color = color, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}