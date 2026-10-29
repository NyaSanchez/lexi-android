package io.github.nyasanchez.lexi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF6D28D9),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF0E7FF),
    onPrimaryContainer = Color(0xFF32106A),
    background = Color(0xFFFAF8FF),
    onBackground = Color(0xFF1B1B35),
    surface = Color(0xFFFAF8FF),
    onSurface = Color(0xFF1B1B35),
    onSurfaceVariant = Color(0xFF565269),
    outline = Color(0xFF82788F),
    outlineVariant = Color(0xFFE4DDEB),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFCBB2FF),
    onPrimary = Color(0xFF3A086C),
    primaryContainer = Color(0xFF302043),
    onPrimaryContainer = Color(0xFFEBDCFF),
    background = Color(0xFF16121F),
    onBackground = Color(0xFFF1EAFB),
    surface = Color(0xFF16121F),
    onSurface = Color(0xFFF1EAFB),
    onSurfaceVariant = Color(0xFFCAC2D9),
    outline = Color(0xFF998CA9),
    outlineVariant = Color(0xFF41374F),
)

@Composable
fun LexiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
