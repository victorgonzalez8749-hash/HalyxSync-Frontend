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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.halyxsynck.components.PrimaryButton
import com.halyxsynck.components.PrimaryTextField
import com.halyxsynck.components.RegisterForm
import com.halyxsynck.model.Catalogos
import com.halyxsynck.model.RegisterData
import com.halyxsynck.navigation.Navigator
import com.halyxsynck.navigation.Screen
import com.halyxsynck.session.RegisterSession
import com.halyxsynck.theme.*
import com.halyxsynck.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {

    val viewModel = RegisterViewModel()
    val scope = rememberCoroutineScope()

    var data by remember {
        mutableStateOf(RegisterData(rol = RegisterSession.rol))
    }

    val esDoctor = RegisterSession.rol == "DOCTOR"

    var textoBusquedaPadecimiento by remember { mutableStateOf("") }
    var expandidoPadecimiento by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(PrimaryBlue, PurpleAccent, GradientEnd))),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (esDoctor) Icons.Default.MedicalServices else Icons.Default.PersonAdd,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(34.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = if (esDoctor) "Registro de Doctor" else "Registro de Paciente",
            color = PrimaryBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (esDoctor) "Crea tu cuenta profesional" else "Crea tu cuenta para comenzar",
            color = TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                RegisterForm(
                    data = data,
                    onNombreChange = { data = data.copy(nombre = it) },
                    onApellidoPaternoChange = { data = data.copy(apellidoPaterno = it) },
                    onApellidoMaternoChange = { data = data.copy(apellidoMaterno = it) },
                    onCorreoChange = { data = data.copy(correo = it) },
                    onTelefonoChange = { data = data.copy(telefono = it) },
                    onPasswordChange = { data = data.copy(contrasena = it) },
                    onConfirmPasswordChange = { data = data.copy(confirmarContrasena = it) },
                    onCedulaChange = { data = data.copy(cedulaProfesional = it) },
                    onEspecialidadChange = { data = data.copy(especialidad = it) }
                )

            }
        }

        if (!esDoctor) {

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MonitorHeart, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Información de salud", color = PurpleAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    PrimaryTextField(
                        value = data.edad,
                        onValueChange = { data = data.copy(edad = it) },
                        label = "Edad",
                        placeholder = "Ej. 28"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Sexo", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        listOf("Masculino", "Femenino", "Otro").forEach { opcion ->
                            FilterChip(
                                selected = data.sexo == opcion,
                                onClick = { data = data.copy(sexo = opcion) },
                                label = { Text(opcion, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PurpleAccent,
                                    selectedLabelColor = White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Padecimientos", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))

                    if (data.padecimientos.isNotEmpty()) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            data.padecimientos.forEach { p ->
                                AssistChip(
                                    onClick = { data = data.copy(padecimientos = data.padecimientos - p) },
                                    label = { Text(p, fontSize = 11.sp) },
                                    trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Quitar", modifier = Modifier.size(12.dp)) },
                                    colors = AssistChipDefaults.assistChipColors(containerColor = PurpleAccent.copy(alpha = 0.15f))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    val filtrados = if (textoBusquedaPadecimiento.isBlank()) {
                        Catalogos.padecimientosComunes
                    } else {
                        Catalogos.padecimientosComunes.filter {
                            it.contains(textoBusquedaPadecimiento, ignoreCase = true) && it !in data.padecimientos
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandidoPadecimiento,
                        onExpandedChange = { expandidoPadecimiento = it }
                    ) {
                        OutlinedTextField(
                            value = textoBusquedaPadecimiento,
                            onValueChange = {
                                textoBusquedaPadecimiento = it
                                expandidoPadecimiento = true
                            },
                            label = { Text("Buscar padecimiento") },
                            placeholder = { Text("Ej. Diabetes") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoPadecimiento) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = TextSecondary
                            )
                        )

                        if (filtrados.isNotEmpty()) {
                            ExposedDropdownMenu(
                                expanded = expandidoPadecimiento,
                                onDismissRequest = { expandidoPadecimiento = false }
                            ) {
                                filtrados.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            data = data.copy(padecimientos = data.padecimientos + opcion)
                                            textoBusquedaPadecimiento = ""
                                            expandidoPadecimiento = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                }
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = "Registrarme",
            onClick = {

                scope.launch {

                    if (data.contrasena != data.confirmarContrasena) {
                        viewModel.mostrarMensaje("Las contraseñas no coinciden")
                        return@launch
                    }

                    val registrado = viewModel.registrar(
                        nombre = data.nombre,
                        apellidoPaterno = data.apellidoPaterno,
                        apellidoMaterno = data.apellidoMaterno,
                        correo = data.correo,
                        telefono = data.telefono,
                        contrasena = data.contrasena,
                        cedula = data.cedulaProfesional,
                        especialidad = data.especialidad,
                        edad = data.edad,
                        sexo = data.sexo,
                        padecimientos = data.padecimientos
                    )

                    if (registrado) {
                        Navigator.navigate(Screen.Login)
                    }

                }

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.mensaje.isNotBlank()) {
            Text(
                text = viewModel.mensaje,
                color = if (viewModel.mensaje.contains("correctamente")) Success else Error
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}