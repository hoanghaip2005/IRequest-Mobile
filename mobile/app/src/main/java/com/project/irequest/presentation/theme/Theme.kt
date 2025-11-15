package com.project.irequest.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// IRequest Brand Colors
val PrimaryBlue = Color(0xFF1976D2)      // Material Blue 700
val PrimaryBlueVariant = Color(0xFF1565C0) // Material Blue 800
val SecondaryGreen = Color(0xFF4CAF50)   // Material Green 500
val AccentOrange = Color(0xFFFF9800)     // Material Orange 500
val CustomOrange = Color(0xFFFB8C00)     // Custom Orange

val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF0D47A1),
    
    secondary = SecondaryGreen,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8F5E8),
    onSecondaryContainer = Color(0xFF1B5E20),
    
    tertiary = AccentOrange,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFF3E0),
    onTertiaryContainer = Color(0xFFE65100),
    
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0)
)

val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),          // Lighter blue for dark theme
    onPrimary = Color(0xFF0D47A1),
    primaryContainer = Color(0xFF1565C0),
    onPrimaryContainer = Color(0xFFE3F2FD),
    
    secondary = Color(0xFF81C784),        // Lighter green for dark theme
    onSecondary = Color(0xFF1B5E20),
    secondaryContainer = Color(0xFF388E3C),
    onSecondaryContainer = Color(0xFFE8F5E8),
    
    tertiary = Color(0xFFFFB74D),         // Lighter orange for dark theme
    onTertiary = Color(0xFFE65100),
    tertiaryContainer = Color(0xFFFF8F00),
    onTertiaryContainer = Color(0xFFFFF3E0),
    
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F)
)


@Composable
fun IRequestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}