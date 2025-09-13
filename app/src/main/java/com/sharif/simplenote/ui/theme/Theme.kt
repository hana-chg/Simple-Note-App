package com.sharif.simplenote.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily

private val DarkColorScheme = darkColorScheme(
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBase,
    onPrimary = NeutralWhite,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = NeutralBlack,

    secondary = SecondaryBase,
    onSecondary = NeutralBlack,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = NeutralBlack,

    background = PrimaryBackground,
    onBackground = NeutralBlack,

    surface = NeutralWhite,
    onSurface = NeutralBlack,

    error = ErrorBase,
    onError = NeutralWhite,
    errorContainer = ErrorLight,
    onErrorContainer = NeutralBlack,

    // Add other color mappings as needed
)




@Composable
fun SimpleNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}