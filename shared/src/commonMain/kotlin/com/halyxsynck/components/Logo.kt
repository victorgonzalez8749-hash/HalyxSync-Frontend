package com.halyxsynck.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halyxsynck.theme.PrimaryBlue
import com.halyxsynck.theme.White

@Composable
fun Logo() {

    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(White),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "+",
            color = PrimaryBlue,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

    }

}