package com.halyxsynck.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.halyxsynck.theme.PrimaryBlue

@Composable
fun ForgotPassword(
    onClick: () -> Unit
) {

    Text(
        text = "¿Olvidó su contraseña?",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
        color = PrimaryBlue,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )

}