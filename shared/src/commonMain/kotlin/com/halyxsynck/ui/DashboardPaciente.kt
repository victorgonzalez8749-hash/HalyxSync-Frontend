package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.halyxsynck.FechaHoy
import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.model.CancelarCitaRequest
import com.halyxsynck.model.CitaInfo
import com.halyxsynck.model.EstudioInfo
import com.halyxsynck.model.PacienteInfo
import com.halyxsynck.model.SubirEstudioRequest
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.repository.CitaRepository
import com.halyxsynck.repository.EstudioRepository
import com.halyxsynck.repository.PacienteRepository
import com.halyxsynck.rememberCapturadorFoto
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

private val RojoCorazon = Color(0xFFE53935)
private val VerdeEscudo = Color(0xFF2E8B57)

@OptIn(ExperimentalEncodingApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun DashboardPaciente() {

    val repository = remember { PacienteRepository() }
    val citaRepository = remember { CitaRepository() }
    val estudioRepository = remember { EstudioRepository() }
    val scope = rememberCoroutineScope()

    var info by remember { mutableStateOf<PacienteInfo?>(null) }
    var citas by remember { mutableStateOf<List<CitaInfo>>(emptyList()) }
    var estudios by remember { mutableStateOf<List<EstudioInfo>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    var fotosPendientes by remember { mutableStateOf(listOf<ByteArray>()) }
    var subiendo by remember { mutableStateOf(false) }
    var mensajeEstudio by remember { mutableStateOf("") }
    var imagenSeleccionada by remember { mutableStateOf<String?>(null) }

    var doctorSeleccionado by remember { mutableStateOf("") }
    var expandidoDoctor by remember { mutableStateOf(false) }

    var citaACancelar by remember { mutableStateOf<CitaInfo?>(null) }
    var motivoCancelacion by remember { mutableStateOf("") }
    var cancelando by remember { mutableStateOf(false) }

    val fechaHoy = remember { FechaHoy.obtener() }

    val citasProximas = citas.filter { it.fecha >= fechaHoy && it.estado != "Cancelada" }

    val tomarFoto = rememberCapturadorFoto { bytes ->
        if (fotosPendientes.size < 3) {
            fotosPendientes = fotosPendientes + bytes
        }
    }

    suspend fun recargarCitas() {
        citas = citaRepository.obtenerCitasPaciente(UserSession.correo)
    }

    LaunchedEffect(Unit) {
        info = repository.obtenerInfo(UserSession.correo)
        citas = citaRepository.obtenerCitasPaciente(UserSession.correo)
        estudios = estudioRepository.obtenerEstudios(UserSession.correo)
        cargando = false
        if (info?.medicos?.size == 1) {
            doctorSeleccionado = info!!.medicos.first().nombre
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // Cabecera con esquina curva + marcas de agua decorativas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(Brush.linearGradient(listOf(PrimaryBlue, PurpleAccent, GradientEnd)))
        ) {

            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = White.copy(alpha = 0.10f),
                modifier = Modifier
                    .size(130.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 30.dp, y = (-30).dp)
            )

            Icon(
                Icons.Default.HealthAndSafety,
                contentDescription = null,
                tint = White.copy(alpha = 0.10f),
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (-20).dp, y = 20.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 34.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                            .background(White.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = White, modifier = Modifier.size(48.dp))
                    }
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(White)
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Success)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = info?.nombreCompleto ?: UserSession.nombre,
                    color = White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(White.copy(alpha = 0.18f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Icon(Icons.Default.Badge, contentDescription = null, tint = White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Paciente", color = White, fontSize = 13.sp)
                }

            }

        }

        Column(modifier = Modifier.padding(20.dp)) {

            if (cargando) {

                Box(modifier = Modifier.fillMaxWidth().padding(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else {

                Card(
                    modifier = Modifier.fillMaxWidth().clickable { Navigator.navigate(Screen.Mensajes) },
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.MailOutline, contentDescription = null, tint = White, modifier = Modifier.size(26.dp))
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mensajes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Comunícate con tu médico", fontSize = 12.sp, color = TextSecondary)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (info == null) {

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Inbox, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Todavía no hay historial médico registrado.", color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }

                } else {

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MiniDato(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.Cake,
                            color = SecondaryCyan,
                            titulo = "Edad",
                            valor = "${info!!.edad} años"
                        )
                        MiniDato(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.Wc,
                            color = PurpleAccent,
                            titulo = "Sexo",
                            valor = info!!.sexo
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TarjetaInfo(
                        icono = Icons.Default.MedicalServices,
                        colorIcono = PrimaryBlue,
                        titulo = "Mis Médicos"
                    ) {
                        info!!.medicos.forEach { medico ->
                            FilaConBarra(color = PrimaryBlue) {
                                Text(medico.nombre, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 15.sp)
                                Text(medico.especialidad, color = PrimaryBlue, fontSize = 13.sp)
                                Text("Atiende: ${medico.padecimientos.joinToString(", ")}", color = TextSecondary, fontSize = 13.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TarjetaInfo(
                        icono = Icons.Default.Medication,
                        colorIcono = VerdeEscudo,
                        titulo = "Medicamentos recetados"
                    ) {
                        info!!.medicamentos.forEach { med ->
                            FilaConBarra(color = VerdeEscudo) {
                                Text(med.nombre, fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 15.sp)
                                Text(med.dosis, color = TextSecondary, fontSize = 13.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(med.horario, color = PurpleAccent, fontSize = 13.sp)
                                }
                            }
                        }
                    }

                    if (citasProximas.isNotEmpty()) {

                        Spacer(modifier = Modifier.height(16.dp))

                        TarjetaInfo(
                            icono = Icons.Default.CalendarToday,
                            colorIcono = PurpleAccent,
                            titulo = "Próximas citas"
                        ) {
                            citasProximas.forEach { cita ->
                                FilaConBarra(color = PurpleAccent) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("${cita.fecha} · ${cita.hora}", fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 15.sp, modifier = Modifier.weight(1f))
                                        EtiquetaEstado(estado = cita.estado)
                                    }
                                    Text("Con ${cita.medico} · ${cita.especialidad}", color = TextSecondary, fontSize = 13.sp)
                                    Text(cita.motivo, color = TextSecondary, fontSize = 13.sp)

                                    if (cita.estado == "Pendiente" || cita.estado == "Confirmada") {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Cancelar cita",
                                            color = Error,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier
                                                .clickable {
                                                    citaACancelar = cita
                                                    motivoCancelacion = ""
                                                }
                                        )
                                    }
                                }
                            }
                        }

                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(SecondaryCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Biotech, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Mis Estudios", color = SecondaryCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (estudios.isEmpty() && fotosPendientes.isEmpty()) {
                            Text("Todavía no has subido ningún estudio.", color = TextSecondary, fontSize = 14.sp)
                        } else {
                            estudios.forEach { estudio ->
                                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Description, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(estudio.descripcion.ifBlank { "Estudio" }, color = TextPrimary, fontSize = 14.sp)
                                            if (estudio.doctorNombre.isNotBlank()) {
                                                Text("Enviado a Dr. ${estudio.doctorNombre}", color = TextSecondary, fontSize = 12.sp)
                                            }
                                        }
                                        if (estudio.fecha.isNotBlank()) {
                                            Text("· ${estudio.fecha}", color = TextSecondary, fontSize = 13.sp)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    AsyncImage(
                                        model = estudio.url,
                                        contentDescription = "Estudio médico",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(190.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .clickable { imagenSeleccionada = estudio.url },
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Subir nuevo estudio", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                        Spacer(modifier = Modifier.height(10.dp))

                        if (info != null && info!!.medicos.isNotEmpty()) {

                            ExposedDropdownMenuBox(
                                expanded = expandidoDoctor,
                                onExpandedChange = { expandidoDoctor = it }
                            ) {
                                OutlinedTextField(
                                    value = doctorSeleccionado,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("¿A qué doctor se lo mandas?") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoDoctor) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PrimaryBlue,
                                        unfocusedBorderColor = TextSecondary
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandidoDoctor,
                                    onDismissRequest = { expandidoDoctor = false }
                                ) {
                                    info!!.medicos.forEach { medico ->
                                        DropdownMenuItem(
                                            text = { Text("${medico.nombre} · ${medico.especialidad}") },
                                            onClick = {
                                                doctorSeleccionado = medico.nombre
                                                expandidoDoctor = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                            repeat(3) { index ->

                                Box(
                                    modifier = Modifier
                                        .size(78.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            if (index < fotosPendientes.size) Success.copy(alpha = 0.15f)
                                            else Background
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (index < fotosPendientes.size) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Success, modifier = Modifier.size(32.dp))
                                    } else if (index == fotosPendientes.size) {

                                        IconButton(onClick = { tomarFoto() }) {
                                            Box(
                                                modifier = Modifier
                                                    .size(52.dp)
                                                    .clip(CircleShape)
                                                    .background(if (doctorSeleccionado.isNotBlank()) PurpleAccent else TextSecondary.copy(alpha = 0.4f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Default.CameraAlt, contentDescription = "Tomar foto", tint = White, modifier = Modifier.size(26.dp))
                                            }
                                        }

                                    } else {
                                        Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = TextSecondary.copy(alpha = 0.3f), modifier = Modifier.size(26.dp))
                                    }
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("${fotosPendientes.size} / 3 fotos nuevas", color = TextSecondary, fontSize = 13.sp)

                        if (doctorSeleccionado.isBlank() && fotosPendientes.isEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Selecciona un doctor antes de tomar la foto", color = TextSecondary, fontSize = 12.sp)
                        }

                        if (fotosPendientes.isNotEmpty()) {

                            Spacer(modifier = Modifier.height(14.dp))

                            PrimaryButton(
                                text = if (subiendo) "Subiendo..." else "Subir estudio",
                                onClick = {

                                    if (subiendo || doctorSeleccionado.isBlank()) return@PrimaryButton

                                    scope.launch {

                                        subiendo = true

                                        val fechaEstudio = ""

                                        var exitoTotal = true

                                        fotosPendientes.forEach { bytes ->
                                            val base64 = Base64.encode(bytes)
                                            val ok = estudioRepository.subirEstudio(
                                                SubirEstudioRequest(
                                                    correoPaciente = UserSession.correo,
                                                    correoDoctor = info!!.medicos.first { it.nombre == doctorSeleccionado }.correo,
                                                    imagenBase64 = base64,
                                                    descripcion = "Estudio médico",
                                                    fecha = fechaEstudio
                                                )
                                            )
                                            if (!ok) exitoTotal = false
                                        }

                                        mensajeEstudio = if (exitoTotal) "Estudio subido correctamente" else "Hubo un error al subir alguna foto"

                                        if (exitoTotal) {
                                            fotosPendientes = listOf()
                                            estudios = estudioRepository.obtenerEstudios(UserSession.correo)
                                        }

                                        subiendo = false

                                    }

                                }
                            )

                        }

                        if (mensajeEstudio.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(mensajeEstudio, color = if (mensajeEstudio.contains("correctamente")) Success else Error, fontSize = 14.sp)
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
                    Navigator.navigateAndClear(Screen.Login)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

    }

    if (imagenSeleccionada != null) {
        Dialog(onDismissRequest = { imagenSeleccionada = null }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { imagenSeleccionada = null },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imagenSeleccionada,
                    contentDescription = "Estudio en pantalla completa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    if (citaACancelar != null) {
        AlertDialog(
            onDismissRequest = { citaACancelar = null },
            title = { Text("Cancelar cita") },
            text = {
                Column {
                    Text(
                        "¿Por qué deseas cancelar tu cita del ${citaACancelar!!.fecha} a las ${citaACancelar!!.hora}?",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = motivoCancelacion,
                        onValueChange = { motivoCancelacion = it },
                        label = { Text("Motivo de la cancelación") },
                        placeholder = { Text("Ej. Ya no puedo asistir") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (motivoCancelacion.isNotBlank()) {
                            scope.launch {
                                cancelando = true
                                val ok = citaRepository.cancelarCita(
                                    CancelarCitaRequest(
                                        citaId = citaACancelar!!.id,
                                        motivoCancelacion = motivoCancelacion
                                    )
                                )
                                if (ok) {
                                    recargarCitas()
                                }
                                citaACancelar = null
                                cancelando = false
                            }
                        }
                    }
                ) {
                    Text("Confirmar cancelación", color = Error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { citaACancelar = null }) {
                    Text("Cerrar", color = TextSecondary)
                }
            }
        )
    }

}

@Composable
private fun FilaConBarra(color: Color, contenido: @Composable ColumnScope.() -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .padding(vertical = 2.dp)
                .background(color, RoundedCornerShape(2.dp))
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f), content = contenido)
    }
}

@Composable
private fun EtiquetaEstado(estado: String) {
    val color = when (estado) {
        "Cancelada" -> Error
        "Confirmada" -> Success
        else -> PurpleAccent
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 9.dp, vertical = 4.dp)
    ) {
        Text(estado, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MiniDato(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    color: Color,
    titulo: String,
    valor: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(titulo, fontSize = 12.sp, color = TextSecondary)
            Text(valor, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}

@Composable
private fun TarjetaInfo(
    icono: ImageVector,
    colorIcono: Color,
    titulo: String,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(11.dp))
                        .background(colorIcono.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(titulo, color = colorIcono, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            contenido()

        }
    }
}