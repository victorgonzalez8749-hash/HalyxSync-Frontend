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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.components.PrimaryButton
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

@OptIn(ExperimentalEncodingApi::class)
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

    val tomarFoto = rememberCapturadorFoto { bytes ->
        if (fotosPendientes.size < 3) {
            fotosPendientes = fotosPendientes + bytes
        }
    }

    LaunchedEffect(Unit) {
        info = repository.obtenerInfo(UserSession.correo)
        citas = citaRepository.obtenerCitasPaciente(UserSession.correo)
        estudios = estudioRepository.obtenerEstudios(UserSession.correo)
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
                .background(Brush.linearGradient(listOf(PrimaryBlue, PurpleAccent, GradientEnd)))
                .padding(vertical = 32.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = White, modifier = Modifier.size(44.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = info?.nombreCompleto ?: UserSession.nombre,
                color = White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Badge, contentDescription = null, tint = White.copy(alpha = 0.85f), modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Paciente", color = White.copy(alpha = 0.85f), fontSize = 13.sp)
            }

        }

        Column(modifier = Modifier.padding(20.dp)) {

            if (cargando) {

                Box(modifier = Modifier.fillMaxWidth().padding(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else {

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

                    Spacer(modifier = Modifier.height(14.dp))

                    TarjetaInfo(
                        icono = Icons.Default.MedicalServices,
                        colorIcono = PrimaryBlue,
                        titulo = "Médico asignado"
                    ) {
                        Text(info!!.medicoAsignado, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Text(info!!.especialidadMedico, color = TextSecondary, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TarjetaInfo(
                        icono = Icons.Default.Assignment,
                        colorIcono = SecondaryCyan,
                        titulo = "Padecimientos"
                    ) {
                        info!!.padecimientos.forEach { p ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                                Icon(Icons.Default.FiberManualRecord, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(8.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(p, color = TextPrimary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TarjetaInfo(
                        icono = Icons.Default.Medication,
                        colorIcono = Success,
                        titulo = "Medicamentos recetados"
                    ) {
                        info!!.medicamentos.forEach { med ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Medication, contentDescription = null, tint = Success, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(med.nombre, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }
                                Text(med.dosis, color = TextSecondary, fontSize = 13.sp, modifier = Modifier.padding(start = 22.dp))
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 22.dp)) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(med.horario, color = PurpleAccent, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    if (citas.isNotEmpty()) {

                        Spacer(modifier = Modifier.height(14.dp))

                        TarjetaInfo(
                            icono = Icons.Default.CalendarToday,
                            colorIcono = PurpleAccent,
                            titulo = "Próximas citas"
                        ) {
                            citas.forEach { cita ->
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Event, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("${cita.fecha} · ${cita.hora}", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                    }
                                    Text("Con ${cita.medico} · ${cita.especialidad}", color = TextSecondary, fontSize = 13.sp, modifier = Modifier.padding(start = 22.dp))
                                    Text(cita.motivo, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.padding(start = 22.dp))
                                }
                            }
                        }

                    }

                }

                Spacer(modifier = Modifier.height(14.dp))

                // ---------- SECCIÓN: MIS ESTUDIOS ----------

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(SecondaryCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Biotech, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Mis Estudios", color = SecondaryCyan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (estudios.isEmpty() && fotosPendientes.isEmpty()) {
                            Text("Todavía no has subido ningún estudio.", color = TextSecondary, fontSize = 13.sp)
                        } else {
                            estudios.forEach { estudio ->
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                                    Icon(Icons.Default.Description, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(estudio.descripcion.ifBlank { "Estudio" }, color = TextPrimary, fontSize = 13.sp)
                                    if (estudio.fecha.isNotBlank()) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("· ${estudio.fecha}", color = TextSecondary, fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                            repeat(3) { index ->

                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(
                                            if (index < fotosPendientes.size) Success.copy(alpha = 0.15f)
                                            else Background
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (index < fotosPendientes.size) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Success, modifier = Modifier.size(28.dp))
                                    } else if (index == fotosPendientes.size) {
                                        IconButton(onClick = { tomarFoto() }) {
                                            Box(
                                                modifier = Modifier
                                                    .size(46.dp)
                                                    .clip(CircleShape)
                                                    .background(PurpleAccent),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Default.CameraAlt, contentDescription = "Tomar foto", tint = White, modifier = Modifier.size(22.dp))
                                            }
                                        }
                                    } else {
                                        Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = TextSecondary.copy(alpha = 0.3f), modifier = Modifier.size(22.dp))
                                    }
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("${fotosPendientes.size} / 3 fotos", color = TextSecondary, fontSize = 12.sp)

                        if (fotosPendientes.isNotEmpty()) {

                            Spacer(modifier = Modifier.height(12.dp))

                            PrimaryButton(
                                text = if (subiendo) "Subiendo..." else "Subir estudio",
                                onClick = {

                                    if (subiendo) return@PrimaryButton

                                    scope.launch {

                                        subiendo = true

                                        val fechaHoy = ""

                                        var exitoTotal = true

                                        fotosPendientes.forEach { bytes ->
                                            val base64 = Base64.encode(bytes)
                                            val ok = estudioRepository.subirEstudio(
                                                SubirEstudioRequest(
                                                    correoPaciente = UserSession.correo,
                                                    imagenBase64 = base64,
                                                    descripcion = "Estudio médico",
                                                    fecha = fechaHoy
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
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(mensajeEstudio, color = if (mensajeEstudio.contains("correctamente")) Success else Error, fontSize = 13.sp)
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

}

@Composable
private fun MiniDato(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    color: androidx.compose.ui.graphics.Color,
    titulo: String,
    valor: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(titulo, fontSize = 11.sp, color = TextSecondary)
            Text(valor, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}

@Composable
private fun TarjetaInfo(
    icono: ImageVector,
    colorIcono: androidx.compose.ui.graphics.Color,
    titulo: String,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(colorIcono.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(titulo, color = colorIcono, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            contenido()

        }
    }
}