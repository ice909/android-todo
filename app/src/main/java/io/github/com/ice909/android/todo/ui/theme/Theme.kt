package io.github.com.ice909.android.todo.ui.theme

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
    primary = Color(0xFF030213),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFD9DBF8),
    onSecondary = Color(0xFF030213),
    tertiaryContainer = Color.Red,
    onTertiaryContainer = Color(0xFFBFA44B),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF252525),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF252525),
    surfaceVariant = Color(0xFF717182),
    error = Color(0xFFD4183D),
    onError = Color(0xFFFFFFFF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF030213),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFD9DBF8),
    onSecondary = Color(0xFF030213),
    tertiary = Color.Red,
    tertiaryContainer = Color(0xFFBFA44B),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF252525),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF252525),
    surfaceVariant = Color(0xFF717182),
    error = Color(0xFFD4183D),
    onError = Color(0xFFFFFFFF),
)

@Composable
fun TodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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