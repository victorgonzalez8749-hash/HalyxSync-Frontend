package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
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

            ModalDrawerSheet {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    DoctorAvatar(size = 64.dp)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Dr. ${UserSession.nombre}",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Mis Pacientes") },
                    icon = { Icon(Icons.Default.People, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        Navigator.navigate(Screen.MisPacientes)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Mensajes") },
                    icon = { Icon(Icons.Default.MailOutline, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // Pendiente: pantalla de mensajes
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                    selected = false,
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
                    title = { Text("HALYX SYNC") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Home, contentDescription = "Menú")
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
                    .padding(24.dp)
            ) {

                Text(
                    text = "Bienvenido, Dr. ${UserSession.nombre}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Usa el menú lateral para ver a tus pacientes.",
                    color = TextSecondary
                )

            }

        }

    }

}