package com.halyxsynck.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halyxsynck.model.RegisterData

@Composable
fun RegisterForm(

    data: RegisterData,

    onNombreChange: (String) -> Unit,

    onApellidoPaternoChange: (String) -> Unit,

    onApellidoMaternoChange: (String) -> Unit,

    onCorreoChange: (String) -> Unit,

    onTelefonoChange: (String) -> Unit,

    onPasswordChange: (String) -> Unit,

    onConfirmPasswordChange: (String) -> Unit

) {

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