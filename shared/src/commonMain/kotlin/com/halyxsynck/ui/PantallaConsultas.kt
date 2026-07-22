package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.PrimaryTextField
import com.halyxsynck.model.AgendarCitaRequest
import com.halyxsynck.model.PacienteResumen
import com.halyxsynck.repository.CitaRepository
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PantallaConsultas() {

    val doctorRepository = remember { DoctorRepository() }
    val citaRepository = remember { CitaRepository() }
    val scope = rememberCoroutineScope()

    var pacientes by remember { mutableStateOf<List<PacienteResumen>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    var pacienteSeleccionado by remember { mutableStateOf<PacienteResumen?>(null) }
    var expandido by remember { mutableStateOf(false) }
    var textoBusqueda by remember { mutableStateOf("") }

    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }

    var mostrarDatePicker by remember { mutableStateOf(false) }
    var mostrarTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = true)

    var mensaje by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        pacientes = doctorRepository.obtenerPacientes(UserSession.correo)
        cargando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Consulta", fontWeight = FontWeight.Bold) },
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
                .padding(20.dp)
        ) {

            if (cargando) {

                Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else if (pacientes.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.EventBusy, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Necesitas al menos un paciente registrado para agendar una consulta.", color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }

            } else {

                val pacientesFiltrados = if (textoBusqueda.isBlank()) {
                    pacientes
                } else {
                    pacientes.filter { it.nombreCompleto.contains(textoBusqueda, ignoreCase = true) }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Busca al paciente", color = PurpleAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        ExposedDropdownMenuBox(
                            expanded = expandido,
                            onExpandedChange = { expandido = it }
                        ) {

                            OutlinedTextField(
                                value = textoBusqueda,
                                onValueChange = {
                                    textoBusqueda = it
                                    pacienteSeleccionado = null
                                    expandido = true
                                },
                                label = { Text("Buscar paciente") },
                                placeholder = { Text("Ej. Vicente") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PrimaryBlue,
                                    unfocusedBorderColor = TextSecondary
                                )
                            )

                            if (pacientesFiltrados.isNotEmpty()) {

                                ExposedDropdownMenu(
                                    expanded = expandido,
                                    onDismissRequest = { expandido = false }
                                ) {
                                    pacientesFiltrados.forEach { paciente ->
                                        DropdownMenuItem(
                                            text = { Text("${paciente.nombreCompleto} · ${paciente.edad} años") },
                                            onClick = {
                                                pacienteSeleccionado = paciente
                                                textoBusqueda = paciente.nombreCompleto
                                                expandido = false
                                            }
                                        )
                                    }
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = fecha,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Fecha") },
                            placeholder = { Text("Selecciona una fecha") },
                            trailingIcon = {
                                IconButton(onClick = { mostrarDatePicker = true }) {
                                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = PurpleAccent)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().clickable { mostrarDatePicker = true },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = TextSecondary
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = hora,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Hora") },
                            placeholder = { Text("Selecciona una hora") },
                            trailingIcon = {
                                IconButton(onClick = { mostrarTimePicker = true }) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, tint = PurpleAccent)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().clickable { mostrarTimePicker = true },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = TextSecondary
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        PrimaryTextField(
                            value = motivo,
                            onValueChange = { motivo = it },
                            label = "Motivo",
                            placeholder = "Ej. Revisión de resultados"
                        )

                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                PrimaryButton(
                    text = "Agendar consulta",
                    onClick = {

                        val paciente = pacienteSeleccionado

                        if (paciente == null || fecha.isBlank() || hora.isBlank()) {
                            mensaje = "Completa todos los campos"
                            return@PrimaryButton
                        }

                        scope.launch {

                            val request = AgendarCitaRequest(
                                correoPaciente = paciente.correo,
                                correoDoctor = UserSession.correo,
                                medico = UserSession.nombre,
                                especialidad = UserSession.especialidad,
                                fecha = fecha,
                                hora = hora,
                                motivo = motivo
                            )

                            val exito = citaRepository.agendarCita(request)

                            mensaje = if (exito) "Consulta agendada correctamente" else "No se pudo agendar la consulta"

                            if (exito) {
                                fecha = ""
                                hora = ""
                                motivo = ""
                                pacienteSeleccionado = null
                                textoBusqueda = ""
                            }

                        }

                    }
                )

                if (mensaje.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(mensaje, color = if (mensaje.contains("correctamente")) Success else Error)
                }

            }

        }

    }

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val fechaSeleccionada = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.UTC).date
                        fecha = fechaSeleccionada.toString()
                    }
                    mostrarDatePicker = false
                }) {
                    Text("Aceptar", color = PurpleAccent)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (mostrarTimePicker) {
        AlertDialog(
            onDismissRequest = { mostrarTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val h = timePickerState.hour.toString().padStart(2, '0')
                    val m = timePickerState.minute.toString().padStart(2, '0')
                    hora = "$h:$m"
                    mostrarTimePicker = false
                }) {
                    Text("Aceptar", color = PurpleAccent)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarTimePicker = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

}