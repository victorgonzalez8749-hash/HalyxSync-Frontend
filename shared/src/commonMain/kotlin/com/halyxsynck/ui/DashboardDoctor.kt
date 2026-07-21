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
import com.halyxsynck.components.DoctorAvatar
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*
import kotlinx.coroutines.launch

@Composable
fun DashboardDoctor() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = White) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd)))
                        .padding(vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DoctorAvatar(size = 80.dp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Dr. ${UserSession.nombre}", color = White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Text(UserSession.correo, color = White.copy(alpha = 0.8f), fontSize = 12.sp)
                }

                HorizontalDivider(color = Border)
                Spacer(modifier = Modifier.height(6.dp))

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null, tint = PurpleAccent) },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))

                NavigationDrawerItem(
                    label = { Text("Mis Pacientes") },
                    icon = { Icon(Icons.Default.People, contentDescription = null, tint = PurpleAccent) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.MisPacientes)
                    }
                )
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))

                NavigationDrawerItem(
                    label = { Text("Mensajes") },
                    icon = { Icon(Icons.Default.MailOutline, contentDescription = null, tint = PurpleAccent) },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))

                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(color = Border)

                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Error) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = Error),
                    onClick = {
                        UserSession.nombre = ""
                        UserSession.rol = ""
                        UserSession.correo = ""
                        Navigator.navigateAndClear(Screen.Login)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
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
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
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

                Text(
                    text = "Bienvenido, Dr. ${UserSession.nombre}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TarjetaStat(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Default.People,
                        colorIcono = PurpleAccent,
                        titulo = "Pacientes",
                        valor = "—"
                    )
                    TarjetaStat(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Default.CalendarToday,
                        colorIcono = SecondaryCyan,
                        titulo = "Citas hoy",
                        valor = "—"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Accesos rápidos", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AccesoRapido(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Default.People,
                        colorFondo = PurpleAccent,
                        texto = "Mis Pacientes",
                        onClick = { Navigator.navigate(Screen.MisPacientes) }
                    )
                    AccesoRapido(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Default.MedicalServices,
                        colorFondo = PrimaryBlue,
                        texto = "Consultas",
                        onClick = { }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AccesoRapido(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Default.Medication,
                        colorFondo = Success,
                        texto = "Recetas",
                        onClick = { }
                    )
                    AccesoRapido(
                        modifier = Modifier.weight(1f),
                        icono = Icons.Default.LocalHospital,
                        colorFondo = SecondaryCyan,
                        texto = "Especialidad",
                        onClick = { Navigator.navigate(Screen.PerfilDoctor) }
                    )
                }

            }

        }

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
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorIcono.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(titulo, fontSize = 12.sp, color = TextSecondary)
            Text(valor, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
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
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(colorFondo),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = White, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(texto, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary, textAlign = TextAlign.Center)
        }
    }
}