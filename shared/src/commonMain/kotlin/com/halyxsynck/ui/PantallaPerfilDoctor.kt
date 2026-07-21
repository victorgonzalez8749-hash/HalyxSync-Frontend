package com.halyxsynck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.session.UserSession
import com.halyxsynck.theme.*

@Composable
fun PantallaPerfilDoctor() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold) },
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
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(PrimaryBlue, PurpleAccent, GradientEnd)))
                    .padding(vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.MedicalServices, contentDescription = null, tint = White, modifier = Modifier.size(46.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Dr. ${UserSession.nombre}", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(White.copy(alpha = 0.18f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Icon(Icons.Default.Verified, contentDescription = null, tint = SecondaryCyan, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Médico verificado", color = White, fontSize = 12.sp)
                }

            }

            Column(modifier = Modifier.padding(20.dp)) {

                FilaPerfil(icono = Icons.Default.Email, color = SecondaryCyan, titulo = "Correo", valor = UserSession.correo)
                Spacer(modifier = Modifier.height(12.dp))
                FilaPerfil(
                    icono = Icons.Default.LocalHospital,
                    color = PurpleAccent,
                    titulo = "Especialidad",
                    valor = if (UserSession.especialidad.isNotBlank()) UserSession.especialidad else "No especificada"
                )
                Spacer(modifier = Modifier.height(12.dp))
                FilaPerfil(icono = Icons.Default.Badge, color = PrimaryBlue, titulo = "Rol", valor = UserSession.rol)

            }

        }

    }

}

@Composable
private fun FilaPerfil(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color,
    titulo: String,
    valor: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(titulo, fontSize = 11.sp, color = TextSecondary)
                Text(valor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
        }
    }
}