package com.example.mycityapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable



private val darkColorScheme = darkColors(
    primary = replyDarkPrimary,
    onPrimary = replyDarkOnPrimary,
    secondary = replyDarkSecondary,
    onSecondary = replyDarkOnSecondary,
    error = replyDarkError,
    onError = replyDarkOnError,
    background = replyDarkBackground,
    onBackground = replyDarkOnBackground,
    surface = replyDarkSurface,
    onSurface = replyDarkOnSurface,

)

private val lightColorScheme = lightColors(
    primary = replyLightPrimary,
    onPrimary = replyLightOnPrimary,
    secondary = replyLightSecondary,
    onSecondary = replyLightOnSecondary,
    error = replyLightError,
    onError = replyLightOnError,
    background = replyLightBackground,
    onBackground = replyLightOnBackground,
    surface = replyLightSurface,
    onSurface = replyLightOnSurface
)


@Composable
fun MyCityAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}