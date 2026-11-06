package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val FitnessHubColorScheme = darkColorScheme(
    primary = MetallicGold,
    onPrimary = LuxuryBlack,
    primaryContainer = DarkGold,
    onPrimaryContainer = BrightGold,
    secondary = BrightGold,
    onSecondary = LuxuryBlack,
    secondaryContainer = DarkCardElevated,
    onSecondaryContainer = SoftGold,
    tertiary = LuxuryRed,
    onTertiary = TextWhite,
    tertiaryContainer = Color(0xFF4A080C),
    onTertiaryContainer = Color(0xFFFFB4AB),
    background = LuxuryBlack,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkCard,
    onSurfaceVariant = TextLightGray,
    outline = MetallicGold.copy(alpha = 0.4f),
    outlineVariant = DarkDivider,
    error = LuxuryRed,
    onError = TextWhite
)

@Composable
fun FitnessHubTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = LuxuryBlack.toArgb()
                window.navigationBarColor = LuxuryBlack.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = FitnessHubColorScheme,
        typography = Typography,
        content = content
    )
}
