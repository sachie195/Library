package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = InkPrimaryDark,
    onPrimary = InkOnPrimaryDark,
    primaryContainer = InkPrimaryContainerDark,
    onPrimaryContainer = InkOnPrimaryContainerDark,
    secondary = AmberSecondaryDark,
    onSecondary = AmberOnSecondaryDark,
    secondaryContainer = AmberSecondaryContainerDark,
    onSecondaryContainer = AmberOnSecondaryContainerDark,
    tertiary = BurgundyTertiaryDark,
    onTertiary = BurgundyOnTertiaryDark,
    background = NightBackgroundDark,
    surface = NightSurfaceDark,
    surfaceVariant = NightSurfaceVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = InkPrimaryLight,
    onPrimary = InkOnPrimaryLight,
    primaryContainer = InkPrimaryContainerLight,
    onPrimaryContainer = InkOnPrimaryContainerLight,
    secondary = AmberSecondaryLight,
    onSecondary = AmberOnSecondaryLight,
    secondaryContainer = AmberSecondaryContainerLight,
    onSecondaryContainer = AmberOnSecondaryContainerLight,
    tertiary = BurgundyTertiaryLight,
    onTertiary = BurgundyOnTertiaryLight,
    background = PaperBackgroundLight,
    surface = PaperSurfaceLight,
    surfaceVariant = PaperSurfaceVariantLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep our crafted literary palette by default
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
