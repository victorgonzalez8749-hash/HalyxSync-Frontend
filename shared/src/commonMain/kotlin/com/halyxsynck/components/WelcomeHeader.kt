package com.halyxsynck.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.theme.TextPrimary
import com.halyxsynck.theme.TextSecondary

@Composable
fun WelcomeHeader() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Bienvenido a HALYX SYNC",
            color = PrimaryBlue,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = androidx.compose.ui.Modifier.height(12.dp))

        Text(
            text = "Acceso seguro a su ecosistema\nde salud inteligente.",
            color = TextSecondary,
            fontSize = 16.sp
        )

    }

}