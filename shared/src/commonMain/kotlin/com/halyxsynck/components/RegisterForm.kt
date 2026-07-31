package com.halyxsynck.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halyxsynck.model.Especialidades
import com.halyxsynck.model.RegisterData
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm(

    data: RegisterData,

    onNombreChange: (String) -> Unit,

    onApellidoPaternoChange: (String) -> Unit,

    onApellidoMaternoChange: (String) -> Unit,

    onCorreoChange: (String) -> Unit,

    onTelefonoChange: (String) -> Unit,

    onPasswordChange: (String) -> Unit,

    onConfirmPasswordChange: (String) -> Unit,

    onCedulaChange: (String) -> Unit,

    onEspecialidadChange: (String) -> Unit

) {

    var expandidoEspecialidad by remember { mutableStateOf(false) }

    Column {

        PrimaryTextField(
            value = data.nombre,
            onValueChange = onNombreChange,
            label = "Nombre(s)",
            placeholder = "Ingrese su nombre"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = data.apellidoPaterno,
            onValueChange = onApellidoPaternoChange,
            label = "Apellido paterno",
            placeholder = "Apellido paterno"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = data.apellidoMaterno,
            onValueChange = onApellidoMaternoChange,
            label = "Apellido materno",
            placeholder = "Apellido materno"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = data.correo,
            onValueChange = onCorreoChange,
            label = "Correo electrónico",
            placeholder = "nombre@correo.com"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = data.telefono,
            onValueChange = onTelefonoChange,
            label = "Teléfono",
            placeholder = "9991234567"
        )

        if (data.rol == "DOCTOR") {

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryTextField(
                value = data.cedulaProfesional,
                onValueChange = onCedulaChange,
                label = "Cédula profesional",
                placeholder = "Ingrese su cédula"
            )

            Spacer(modifier = Modifier.height(16.dp))

            val especialidadesFiltradas = if (data.especialidad.isBlank()) {
                Especialidades.lista
            } else {
                Especialidades.lista.filter { it.contains(data.especialidad, ignoreCase = true) }
            }

            ExposedDropdownMenuBox(
                expanded = expandidoEspecialidad,
                onExpandedChange = { expandidoEspecialidad = it }
            ) {

                OutlinedTextField(
                    value = data.especialidad,
                    onValueChange = {
                        onEspecialidadChange(it)
                        expandidoEspecialidad = true
                    },
                    label = { Text("Especialidad") },
                    placeholder = { Text("Busca tu especialidad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEspecialidad) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = TextSecondary
                    )
                )

                if (especialidadesFiltradas.isNotEmpty()) {
                    ExposedDropdownMenu(
                        expanded = expandidoEspecialidad,
                        onDismissRequest = { expandidoEspecialidad = false }
                    ) {
                        especialidadesFiltradas.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    onEspecialidadChange(opcion)
                                    expandidoEspecialidad = false
                                }
                            )
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = data.contrasena,
            onValueChange = onPasswordChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = data.confirmarContrasena,
            onValueChange = onConfirmPasswordChange,
            label = "Confirmar contraseña"
        )

    }

}