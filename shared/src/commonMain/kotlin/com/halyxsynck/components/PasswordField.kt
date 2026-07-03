package com.halyxsynck.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.theme.TextSecondary
import com.halyxsynck.theme.White

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Contraseña"
) {

    val visible = false

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        label = {
            Text(label)
        },
        placeholder = {
            Text("********")
        },
        visualTransformation =
            if (visible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),


        shape = RoundedCornerShape(18.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryBlue,
            unfocusedBorderColor = TextSecondary,
            focusedContainerColor = White,
            unfocusedContainerColor = White
        )

    )

}