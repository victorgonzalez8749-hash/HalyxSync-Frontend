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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.model.RecetaInfo
import com.halyxsynck.rememberGeneradorReceta
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*

private val RojoCorazon = Color(0xFFE53935)
private val VerdeEscudo = Color(0xFF2E8B57)

@Composable
fun PantallaRecetas() {

    val repository = remember { DoctorRepository() }
    val generador = rememberGeneradorReceta()

    var recetas by remember { mutableStateOf<List<RecetaInfo>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        recetas = repository.obtenerRecetas(UserSession.correo)
        cargando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recetas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue, titleContentColor = White)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {

            if (cargando) {

                Box(modifier = Modifier.fillMaxWidth().padding(60.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PurpleAccent)
                }

            } else if (recetas.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.size(90.dp).clip(CircleShape).background(VerdeEscudo.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Medication, contentDescription = null, tint = VerdeEscudo, modifier = Modifier.size(42.dp))
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Text("Todavía no hay recetas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Cuando registres medicamentos para tus pacientes, aparecerán aquí listas para compartir en PDF.",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }

            } else {

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {

                    // Franja decorativa, mismo estilo que los dashboards
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(
                            Icons.Default.Medication to VerdeEscudo,
                            Icons.Default.LocalHospital to RojoCorazon,
                            Icons.Default.Favorite to RojoCorazon,
                            Icons.Default.HealthAndSafety to VerdeEscudo,
                            Icons.Default.MedicalServices to PrimaryBlue,
                            Icons.Default.Vaccines to PrimaryBlue
                        ).forEach { (icono, color) ->
                            Icon(icono, contentDescription = null, tint = color.copy(alpha = 0.20f), modifier = Modifier.size(18.dp))
                        }
                    }

                    recetas.forEach { receta ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(18.dp),
                                    ambientColor = VerdeEscudo.copy(alpha = 0.25f),
                                    spotColor = VerdeEscudo.copy(alpha = 0.25f)
                                ),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = White)
                        ) {
                            Box {

                                Icon(
                                    Icons.Default.Medication,
                                    contentDescription = null,
                                    tint = VerdeEscudo.copy(alpha = 0.07f),
                                    modifier = Modifier.size(90.dp).align(Alignment.TopEnd).offset(x = 20.dp, y = (-20).dp)
                                )

                                Column(modifier = Modifier.padding(18.dp)) {

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(PurpleAccent.copy(alpha = 0.15f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(Icons.Default.Person, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(22.dp))
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(receta.pacienteNombre, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 16.sp)
                                            Text("${receta.edad} años", color = TextSecondary, fontSize = 12.sp)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))
                                    HorizontalDivider()
                                    Spacer(modifier = Modifier.height(12.dp))

                                    receta.medicamentos.forEach { med ->
                                        Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
                                            Box(
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .clip(RoundedCornerShape(9.dp))
                                                    .background(VerdeEscudo.copy(alpha = 0.15f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Default.Medication, contentDescription = null, tint = VerdeEscudo, modifier = Modifier.size(15.dp))
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text("${med.nombre} — ${med.dosis}", fontWeight = FontWeight.SemiBold, color = TextPrimary, fontSize = 14.sp)
                                                Text(med.horario, color = PurpleAccent, fontSize = 12.sp)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = {
                                            generador.generarYCompartir(
                                                receta = receta,
                                                nombreDoctor = UserSession.nombre,
                                                especialidad = UserSession.especialidad
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth().height(48.dp),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = VerdeEscudo)
                                    ) {
                                        Icon(Icons.Default.Share, contentDescription = null, tint = White, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Compartir receta en PDF", color = White, fontWeight = FontWeight.SemiBold)
                                    }

                                }
                            }
                        }

                    }

                }

            }

        }

    }

}