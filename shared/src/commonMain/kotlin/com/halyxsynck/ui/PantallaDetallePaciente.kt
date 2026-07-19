package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    var padecimientos by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }

    var medNombre by remember { mutableStateOf("") }
    var medDosis by remember { mutableStateOf("") }
    var medHorario by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Paciente",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Text(
            text = correo,
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {

            Column(modifier = Modifier.padding(18.dp)) {

                Text("🧑 Datos del paciente", color = PurpleAccent, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(10.dp))

                PrimaryTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = "Edad",
                    placeholder = "Ej. 28"
                )

                Spacer(modifier = Modifier.height(10.dp))

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
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {

            Column(modifier = Modifier.padding(18.dp)) {

                Text("💊 Receta (medicamento)", color = PurpleAccent, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(10.dp))

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
                        padecimientos = padecimientos.split(",").map { it.trim() },
                        medicoAsignado = UserSession.nombre,
                        especialidadMedico = especialidad,
                        medicamentos = listOf(
                            MedicamentoRequest(medNombre, medDosis, medHorario)
                        )
                    )

                    val guardado = repository.registrarHistorial(request)

                    mensaje = if (guardado) "Historial guardado correctamente" else "Error al guardar"

                }

            }
        )

        if (mensaje.isNotBlank()) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = mensaje,
                color = if (mensaje.contains("correctamente")) Success else Error
            )

        }

        Spacer(modifier = Modifier.height(24.dp))

    }

}