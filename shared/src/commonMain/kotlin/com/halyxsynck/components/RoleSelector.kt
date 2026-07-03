package com.halyxsynck.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.theme.TextSecondary
import com.halyxsynck.theme.White

@Composable
fun RoleSelector() {

    var doctorSelected by remember {
        mutableStateOf(true)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Button(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (doctorSelected) PrimaryBlue else White
            ),
            onClick = {
                doctorSelected = true
            }
        ) {

            Text(
                "SOY DOCTOR",
                color = if (doctorSelected) White else TextSecondary
            )

        }

        Button(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!doctorSelected) PrimaryBlue else White
            ),
            onClick = {
                doctorSelected = false
            }
        ) {

            Text(
                "SOY PACIENTE",
                color = if (!doctorSelected) White else TextSecondary
            )

        }

    }

}