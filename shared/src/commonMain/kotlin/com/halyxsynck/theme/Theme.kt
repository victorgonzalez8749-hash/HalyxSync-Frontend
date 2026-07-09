package com.halyxsynck.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val HalyxColorScheme = lightColorScheme(

    primary = PrimaryBlue,
    secondary = SecondaryCyan,
    tertiary = PurpleAccent,

    background = Background,
    surface = Surface,

    onPrimary = White,
    onSecondary = Black,

    onBackground = TextPrimary,
    onSurface = TextPrimary,

    error = Error
)

@Composable
fun HalyxTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = HalyxColorScheme,
        typography = Typography,
        content = content
    )

}