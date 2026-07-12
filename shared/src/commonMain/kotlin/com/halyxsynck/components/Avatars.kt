package com.halyxsynck.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.theme.PurpleAccent
import com.halyxsynck.theme.White

// Avatar de un paciente: figura simple de una persona (cabeza + hombros)
@Composable
fun PatientAvatar(size: androidx.compose.ui.unit.Dp = 90.dp) {

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(White)
    ) {

        Canvas(modifier = Modifier.size(size)) {

            val w = this.size.width
            val h = this.size.height

            // Cabeza (círculo)
            drawCircle(
                color = PrimaryBlue,
                radius = w * 0.18f,
                center = Offset(w / 2f, h * 0.35f),
                style = Fill
            )

            // Hombros/cuerpo (medio óvalo en la parte baja)
            drawArc(
                color = PurpleAccent,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.15f, h * 0.55f),
                size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.6f)
            )

        }

    }

}

// Avatar de un doctor: misma figura base + una "bata" blanca encima
@Composable
fun DoctorAvatar(size: androidx.compose.ui.unit.Dp = 90.dp) {

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(White)
    ) {

        Canvas(modifier = Modifier.size(size)) {

            val w = this.size.width
            val h = this.size.height

            // Cabeza
            drawCircle(
                color = PrimaryBlue,
                radius = w * 0.18f,
                center = Offset(w / 2f, h * 0.35f),
                style = Fill
            )

            // Cuerpo con bata blanca (bata más ancha y clara)
            drawArc(
                color = White,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.1f, h * 0.55f),
                size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.65f)
            )

            // Borde de la bata (línea central, como cierre)
            drawLine(
                color = PurpleAccent,
                start = Offset(w / 2f, h * 0.58f),
                end = Offset(w / 2f, h * 0.98f),
                strokeWidth = w * 0.02f
            )

            // Cuello de la bata (pequeño triángulo/detalle)
            drawCircle(
                color = PurpleAccent,
                radius = w * 0.04f,
                center = Offset(w / 2f, h * 0.56f)
            )

        }

    }

}