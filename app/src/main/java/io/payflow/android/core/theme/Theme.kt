package io.payflow.android.core.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = PayFlowBlueLight,
    secondary = PayFlowTeal,
    tertiary = PayFlowTealLight
)

private val LightColorScheme = lightColorScheme(
    primary = PayFlowBlue,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = PayFlowTeal,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = PayFlowBlueDark,
    background = PayFlowBackground,
    surface = PayFlowSurface,
    onBackground = PayFlowOnSurface,
    onSurface = PayFlowOnSurface
)

@Composable
fun PayFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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