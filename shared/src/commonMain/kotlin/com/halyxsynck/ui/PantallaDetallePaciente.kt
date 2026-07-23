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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.PrimaryTextField
import com.halyxsynck.model.*
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.repository.EstudioRepository
import com.halyxsynck.repository.PacienteRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetallePaciente(correo: String) {

    val doctorRepository = remember { DoctorRepository() }
    val pacienteRepository = remember { PacienteRepository() }
    val scope = rememberCoroutineScope()

    var cargando by remember { mutableStateOf(true) }
    var infoExistente by remember { mutableStateOf<PacienteInfo?>(null) }

    var edad by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Masculino") }
    var padecimientos by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var expandidoEspecialidad by remember { mutableStateOf(false) }

    var medNombre by remember { mutableStateOf("") }
    var expandidoMed by remember { mutableStateOf(false) }
    var medDosis by remember { mutableStateOf("") }
    var expandidoDosis by remember { mutableStateOf(false) }
    var medHorarios by remember { mutableStateOf(listOf<String>()) }
    var medPadecimiento by remember { mutableStateOf("") }
    var medObservaciones by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }

    LaunchedEffect(correo) {
        infoExistente = pacienteRepository.obtenerInfo(correo)
        cargando = false
    }

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
                    Text(if (infoExistente != null) infoExistente!!.nombreCompleto else "Paciente nuevo", fontSize = 12.sp, color = TextSecondary)
                    Text(correo, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (cargando) {

                Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else if (infoExistente == null) {

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

                        BuscadorSimple(
                            valor = especialidad,
                            onValorChange = { especialidad = it },
                            opciones = Especialidades.lista,
                            expandido = expandidoEspecialidad,
                            onExpandidoChange = { expandidoEspecialidad = it },
                            etiqueta = "Tu especialidad",
                            placeholder = "Busca tu especialidad"
                        )

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TarjetaMedicamentoForm(
                    titulo = "Receta médica",
                    medNombre = medNombre, onMedNombreChange = { medNombre = it },
                    expandidoMed = expandidoMed, onExpandidoMedChange = { expandidoMed = it },
                    medDosis = medDosis, onMedDosisChange = { medDosis = it },
                    expandidoDosis = expandidoDosis, onExpandidoDosisChange = { expandidoDosis = it },
                    medHorarios = medHorarios, onMedHorariosChange = { medHorarios = it },
                    medPadecimiento = medPadecimiento, onMedPadecimientoChange = { medPadecimiento = it },
                    medObservaciones = medObservaciones, onMedObservacionesChange = { medObservaciones = it }
                )

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
                                medicamentos = listOf(MedicamentoRequest(medNombre, medDosis, medHorarios.joinToString(",")))
                            )

                            val guardado = doctorRepository.registrarHistorial(request)
                            mensaje = if (guardado) "Historial guardado correctamente" else "Error al guardar"

                        }
                    }
                )

            } else {

                val info = infoExistente!!

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        TituloConIcono(icono = Icons.Default.Assignment, texto = "Historial clínico", color = PurpleAccent)

                        Spacer(modifier = Modifier.height(10.dp))

                        FilaDato("Edad", "${info.edad} años")
                        FilaDato("Sexo", info.sexo)
                        FilaDato("Padecimientos", info.padecimientos.joinToString(", "))
                        FilaDato("Especialidad", info.especialidadMedico)

                        if (info.medicamentos.isNotEmpty()) {

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Medicamentos registrados", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)

                            info.medicamentos.forEach { med ->
                                Column(modifier = Modifier.padding(top = 10.dp)) {
                                    Text("💊 ${med.nombre} — ${med.dosis}", fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 14.sp)
                                    Text("⏰ ${med.horario}", color = PurpleAccent, fontSize = 12.sp)
                                    if (med.padecimiento.isNotBlank()) {
                                        Text("Para: ${med.padecimiento}", color = TextSecondary, fontSize = 12.sp)
                                    }
                                    if (med.observaciones.isNotBlank()) {
                                        Text("Obs: ${med.observaciones}", color = TextSecondary, fontSize = 12.sp)
                                    }
                                }
                            }

                        }

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TarjetaEstudiosPaciente(correo = correo)

                Spacer(modifier = Modifier.height(16.dp))

                TarjetaMedicamentoForm(
                    titulo = "Registrar consulta / nuevo medicamento",
                    medNombre = medNombre, onMedNombreChange = { medNombre = it },
                    expandidoMed = expandidoMed, onExpandidoMedChange = { expandidoMed = it },
                    medDosis = medDosis, onMedDosisChange = { medDosis = it },
                    expandidoDosis = expandidoDosis, onExpandidoDosisChange = { expandidoDosis = it },
                    medHorarios = medHorarios, onMedHorariosChange = { medHorarios = it },
                    medPadecimiento = medPadecimiento, onMedPadecimientoChange = { medPadecimiento = it },
                    medObservaciones = medObservaciones, onMedObservacionesChange = { medObservaciones = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                PrimaryButton(
                    text = "Agregar al expediente",
                    onClick = {
                        scope.launch {

                            val request = AgregarMedicamentoRequest(
                                correoPaciente = correo,
                                medicamento = MedicamentoCompleto(
                                    nombre = medNombre,
                                    dosis = medDosis,
                                    horario = medHorarios.joinToString(","),
                                    padecimiento = medPadecimiento,
                                    observaciones = medObservaciones
                                )
                            )

                            val guardado = pacienteRepository.agregarMedicamento(request)

                            mensaje = if (guardado) "Consulta agregada al expediente" else "Error al guardar"

                            if (guardado) {
                                infoExistente = pacienteRepository.obtenerInfo(correo)
                                medNombre = ""; medDosis = ""; medHorarios = listOf(); medPadecimiento = ""; medObservaciones = ""
                            }

                        }
                    }
                )

            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(mensaje, color = if (mensaje.contains("correctamente") || mensaje.contains("agregada")) Success else Error)
            }

            Spacer(modifier = Modifier.height(24.dp))

        }

    }

}

@Composable
private fun TarjetaEstudiosPaciente(correo: String) {

    val estudioRepository = remember { EstudioRepository() }
    var estudios by remember { mutableStateOf<List<EstudioInfo>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(correo) {
        estudios = estudioRepository.obtenerEstudios(correo)
        cargando = false
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {

            TituloConIcono(icono = Icons.Default.Biotech, texto = "Estudios del paciente", color = SecondaryCyan)

            Spacer(modifier = Modifier.height(10.dp))

            if (cargando) {
                CircularProgressIndicator(color = SecondaryCyan, modifier = Modifier.size(20.dp))
            } else if (estudios.isEmpty()) {
                Text("El paciente no ha subido ningún estudio todavía.", color = TextSecondary, fontSize = 13.sp)
            } else {
                estudios.forEach { estudio ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Description, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(estudio.descripcion.ifBlank { "Estudio" }, color = TextPrimary, fontSize = 13.sp, modifier = Modifier.weight(1f))
                            if (estudio.fecha.isNotBlank()) {
                                Text(estudio.fecha, color = TextSecondary, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        AsyncImage(
                            model = estudio.url,
                            contentDescription = "Estudio médico",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
            }

        }
    }

}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun TarjetaMedicamentoForm(
    titulo: String,
    medNombre: String, onMedNombreChange: (String) -> Unit,
    expandidoMed: Boolean, onExpandidoMedChange: (Boolean) -> Unit,
    medDosis: String, onMedDosisChange: (String) -> Unit,
    expandidoDosis: Boolean, onExpandidoDosisChange: (Boolean) -> Unit,
    medHorarios: List<String>, onMedHorariosChange: (List<String>) -> Unit,
    medPadecimiento: String, onMedPadecimientoChange: (String) -> Unit,
    medObservaciones: String, onMedObservacionesChange: (String) -> Unit
) {

    var mostrarTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(is24Hour = true)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {

            TituloConIcono(icono = Icons.Default.Medication, texto = titulo, color = Success)

            Spacer(modifier = Modifier.height(12.dp))

            BuscadorSimple(
                valor = medNombre,
                onValorChange = onMedNombreChange,
                opciones = Catalogos.medicamentosComunes,
                expandido = expandidoMed,
                onExpandidoChange = onExpandidoMedChange,
                etiqueta = "Nombre del medicamento",
                placeholder = "Ej. Metformina"
            )

            Spacer(modifier = Modifier.height(10.dp))

            BuscadorSimple(
                valor = medDosis,
                onValorChange = onMedDosisChange,
                opciones = Catalogos.dosisComunes,
                expandido = expandidoDosis,
                onExpandidoChange = onExpandidoDosisChange,
                etiqueta = "Dosis",
                placeholder = "Ej. 850 mg"
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Horario de toma", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (medHorarios.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    medHorarios.forEach { hora ->
                        AssistChip(
                            onClick = { onMedHorariosChange(medHorarios - hora) },
                            label = { Text(hora, fontSize = 12.sp) },
                            trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Quitar", modifier = Modifier.size(14.dp)) },
                            colors = AssistChipDefaults.assistChipColors(containerColor = SecondaryCyan.copy(alpha = 0.15f))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedButton(
                onClick = { mostrarTimePicker = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PurpleAccent)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Agregar horario")
            }

            Spacer(modifier = Modifier.height(14.dp))

            PrimaryTextField(
                value = medPadecimiento,
                onValueChange = onMedPadecimientoChange,
                label = "Padecimiento que trata",
                placeholder = "Ej. Diabetes tipo 2"
            )

            Spacer(modifier = Modifier.height(10.dp))

            PrimaryTextField(
                value = medObservaciones,
                onValueChange = onMedObservacionesChange,
                label = "Observaciones de la consulta",
                placeholder = "Ej. Tomar con alimentos"
            )

        }
    }

    if (mostrarTimePicker) {
        AlertDialog(
            onDismissRequest = { mostrarTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val h = timePickerState.hour.toString().padStart(2, '0')
                    val m = timePickerState.minute.toString().padStart(2, '0')
                    val nueva = "$h:$m"
                    if (nueva !in medHorarios) {
                        onMedHorariosChange((medHorarios + nueva).sorted())
                    }
                    mostrarTimePicker = false
                }) {
                    Text("Agregar", color = PurpleAccent)
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

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun BuscadorSimple(
    valor: String,
    onValorChange: (String) -> Unit,
    opciones: List<String>,
    expandido: Boolean,
    onExpandidoChange: (Boolean) -> Unit,
    etiqueta: String,
    placeholder: String
) {
    val filtradas = if (valor.isBlank()) opciones else opciones.filter { it.contains(valor, ignoreCase = true) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = onExpandidoChange
    ) {
        OutlinedTextField(
            value = valor,
            onValueChange = {
                onValorChange(it)
                onExpandidoChange(true)
            },
            label = { Text(etiqueta) },
            placeholder = { Text(placeholder) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = TextSecondary
            )
        )

        if (filtradas.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expandido,
                onDismissRequest = { onExpandidoChange(false) }
            ) {
                filtradas.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onValorChange(opcion)
                            onExpandidoChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilaDato(titulo: String, valor: String) {
    Row(modifier = Modifier.padding(vertical = 3.dp)) {
        Text("$titulo: ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
        Text(valor, fontSize = 13.sp, color = TextPrimary)
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