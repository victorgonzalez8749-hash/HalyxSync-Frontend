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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.FechaHoy
import com.halyxsynck.components.DoctorAvatar
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.repository.DoctorRepository
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch

@Composable
fun DashboardDoctor() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val repository = remember { DoctorRepository() }

    var totalPacientes by remember { mutableStateOf<Int?>(null) }
    var citasHoyCount by remember { mutableStateOf<Int?>(null) }

    val saludo = remember { FechaHoy.obtenerSaludo() }

    LaunchedEffect(Unit) {
        totalPacientes = repository.obtenerPacientes(UserSession.correo).size
        citasHoyCount = repository.obtenerCitasHoy(UserSession.correo, FechaHoy.obtener()).size
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = White) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd)))
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DoctorAvatar(size = 92.dp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Dr. ${UserSession.nombre}", color = White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(UserSession.correo, color = White.copy(alpha = 0.85f), fontSize = 13.sp)
                    if (UserSession.especialidad.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(White.copy(alpha = 0.18f))
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Icon(Icons.Default.LocalHospital, contentDescription = null, tint = White, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(UserSession.especialidad, color = White, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ItemMenuLateral(
                    icono = Icons.Default.Home,
                    texto = "Inicio",
                    onClick = { scope.launch { drawerState.close() } }
                )
                ItemMenuLateral(
                    icono = Icons.Default.People,
                    texto = "Mis Pacientes",
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.MisPacientes)
                    }
                )
                ItemMenuLateral(
                    icono = Icons.Default.CalendarToday,
                    texto = "Citas de Hoy",
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.CitasHoy)
                    }
                )
                ItemMenuLateral(
                    icono = Icons.Default.Medication,
                    texto = "Recetas",
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.Recetas)
                    }
                )
                ItemMenuLateral(
                    icono = Icons.Default.MailOutline,
                    texto = "Mensajes",
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.Mensajes)
                    }
                )
                ItemMenuLateral(
                    icono = Icons.Default.Person,
                    texto = "Mi Perfil",
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.PerfilDoctor)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            UserSession.nombre = ""
                            UserSession.rol = ""
                            UserSession.correo = ""
                            Navigator.navigateAndClear(Screen.Login)
                        },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Error.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Error, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Cerrar sesión", color = Error, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("HALYX SYNC", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PrimaryBlue,
                        titleContentColor = White,
                        navigationIconContentColor = White
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", modifier = Modifier.size(26.dp))
                        }
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.linearGradient(listOf(PrimaryBlue, PurpleAccent, GradientEnd)))
                        .padding(horizontal = 20.dp, vertical = 26.dp)
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(White.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.MedicalServices, contentDescription = null, tint = White, modifier = Modifier.size(32.dp))
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(saludo, color = White.copy(alpha = 0.85f), fontSize = 15.sp)
                            Text(
                                text = "Dr. ${UserSession.nombre}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                            if (UserSession.especialidad.isNotBlank()) {
                                Text(UserSession.especialidad, color = White.copy(alpha = 0.9f), fontSize = 14.sp)
                            }
                        }

                    }

                }

                Column(modifier = Modifier.padding(20.dp)) {

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TarjetaStat(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.People,
                            colorIcono = PurpleAccent,
                            titulo = "Pacientes",
                            valor = totalPacientes?.toString() ?: "—"
                        )
                        TarjetaStat(
                            modifier = Modifier.weight(1f).clickable { Navigator.navigate(Screen.CitasHoy) },
                            icono = Icons.Default.CalendarToday,
                            colorIcono = SecondaryCyan,
                            titulo = "Citas hoy",
                            valor = citasHoyCount?.toString() ?: "—"
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text("Accesos rápidos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                    Spacer(modifier = Modifier.height(14.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { Navigator.navigate(Screen.MisPacientes) },
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.People, contentDescription = null, tint = White, modifier = Modifier.size(30.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Mis Pacientes", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text("Ver y gestionar expedientes", fontSize = 13.sp, color = TextSecondary)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(26.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AccesoRapido(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.MedicalServices,
                            colorFondo = PrimaryBlue,
                            texto = "Consultas",
                            onClick = { Navigator.navigate(Screen.Consultas) }
                        )
                        AccesoRapido(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.Medication,
                            colorFondo = Success,
                            texto = "Recetas",
                            onClick = { Navigator.navigate(Screen.Recetas) }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AccesoRapido(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.MailOutline,
                            colorFondo = SecondaryCyan,
                            texto = "Mensajes",
                            onClick = { Navigator.navigate(Screen.Mensajes) }
                        )
                        AccesoRapido(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.LocalHospital,
                            colorFondo = PurpleAccent,
                            texto = "Mi Perfil",
                            onClick = { Navigator.navigate(Screen.PerfilDoctor) }
                        )
                    }

                }

            }

        }

    }

}

@Composable
private fun ItemMenuLateral(
    icono: ImageVector,
    texto: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(PurpleAccent.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icono, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(texto, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
    }
}

@Composable
private fun TarjetaStat(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    colorIcono: Color,
    titulo: String,
    valor: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colorIcono.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(26.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(titulo, fontSize = 14.sp, color = TextSecondary)
            Text(valor, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}

@Composable
private fun AccesoRapido(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    colorFondo: Color,
    texto: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(colorFondo),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = White, modifier = Modifier.size(30.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(texto, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, textAlign = TextAlign.Center)
        }
    }
}