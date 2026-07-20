package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.model.PacienteResumen
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import androidx.compose.ui.graphics.Brush

@Composable
fun PantallaMisPacientes() {

    val repository = remember { DoctorRepository() }

    var pacientes by remember { mutableStateOf<List<PacienteResumen>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var mostrarDialogoNuevo by remember { mutableStateOf(false) }
    var correoNuevo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        pacientes = repository.obtenerPacientes(UserSession.correo)
        cargando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Pacientes", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogoNuevo = true },
                containerColor = PurpleAccent,
                contentColor = White
            ) {
                Icon(Icons.Default.PersonAddAlt1, contentDescription = "Nuevo paciente")
            }
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

            } else if (pacientes.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("👥", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Todavía no tienes pacientes", color = TextSecondary, fontWeight = FontWeight.SemiBold)
                    Text("Toca el botón + para agregar uno", color = TextSecondary, fontSize = 13.sp)
                }

            } else {

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    items(pacientes) { paciente ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Navigator.navigate(Screen.DetallePaciente(correo = paciente.correo))
                                },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(containerColor = White)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // Avatar circular con iniciales, estilo tu referencia
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = obtenerIniciales(paciente.nombreCompleto),
                                        color = White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(paciente.nombreCompleto, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                    Text(
                                        text = "${paciente.edad} años · ${paciente.sexo}",
                                        color = TextSecondary,
                                        fontSize = 13.sp
                                    )
                                }

                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PurpleAccent)

                            }

                        }

                    }

                }

            }

        }

    }

    // Diálogo para agregar paciente nuevo por correo
    if (mostrarDialogoNuevo) {

        AlertDialog(
            onDismissRequest = { mostrarDialogoNuevo = false },
            title = { Text("Nuevo paciente") },
            text = {
                Column {
                    Text("Ingresa el correo del paciente para comenzar su historial:", color = TextSecondary, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = correoNuevo,
                        onValueChange = { correoNuevo = it },
                        label = { Text("Correo del paciente") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (correoNuevo.isNotBlank()) {
                        val correo = correoNuevo
                        mostrarDialogoNuevo = false
                        correoNuevo = ""
                        Navigator.navigate(Screen.DetallePaciente(correo = correo))
                    }
                }) {
                    Text("Continuar", color = PurpleAccent, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoNuevo = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )

    }

}

private fun obtenerIniciales(nombreCompleto: String): String {
    val partes = nombreCompleto.trim().split(" ")
    return when {
        partes.size >= 2 -> "${partes[0].firstOrNull() ?: ""}${partes[1].firstOrNull() ?: ""}".uppercase()
        partes.isNotEmpty() -> partes[0].take(2).uppercase()
        else -> "?"
    }
}