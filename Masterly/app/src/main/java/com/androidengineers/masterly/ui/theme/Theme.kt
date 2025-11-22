package com.androidengineers.masterly.ui.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightForeground,
    surface = LightCard,
    onSurface = LightCardForeground,
    primary = LightPrimary,
    onPrimary = LightPrimaryForeground,
    secondary = LightSecondary,
    onSecondary = LightSecondaryForeground,
    error = LightDestructive,
    onError = LightDestructiveForeground,
)

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkForeground,
    surface = DarkCard,
    onSurface = DarkCardForeground,
    primary = DarkPrimary,
    onPrimary = DarkPrimaryForeground,
    secondary = DarkSecondary,
    onSecondary = DarkSecondaryForeground,
    error = DarkDestructive,
    onError = DarkDestructiveForeground,
)

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MasterlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val white = Color.White

    val customColorScheme = darkColorScheme(
        primary = Color(0xFFBB86FC),
        onPrimary = white,
        secondary = Color(0xFF03DAC6),
        onSecondary = white,
        background = Color(0xFF121212),
        onBackground = white,
        surface = Color(0xFF1E1E1E),
        onSurface = white,
        tertiary = Color(0xFF03DAC6)
    )

    CompositionLocalProvider(
        LocalContentColor provides white
    ) {
        MaterialTheme(
            colorScheme = customColorScheme,
            typography = MasterlyTypography(),
            content = content
        )
    }
}