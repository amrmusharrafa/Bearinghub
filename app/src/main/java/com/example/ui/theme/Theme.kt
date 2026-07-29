package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryIndustrialLight,
    onPrimary = Color.Black,
    secondary = SteelGrayLight,
    onSecondary = Color.Black,
    tertiary = SafetyOrange,
    background = IndustrialDarkBackground,
    onBackground = Color(0xFFE2E8F0),
    surface = IndustrialDarkSurface,
    onSurface = Color(0xFFE2E8F0),
    surfaceVariant = IndustrialDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryIndustrialBlue,
    onPrimary = Color.White,
    secondary = SteelGray,
    onSecondary = Color.White,
    tertiary = SafetyOrange,
    background = IndustrialLightBackground,
    onBackground = Color(0xFF1E293B),
    surface = IndustrialLightSurface,
    onSurface = Color(0xFF1E293B),
    surfaceVariant = IndustrialLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF64748B)
)

@Composable
fun BearingHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our brand industrial blue palette by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    BearingHubTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
