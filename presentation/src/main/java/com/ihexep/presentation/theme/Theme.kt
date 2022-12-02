package com.ihexep.presentation.theme

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val ColorScheme = darkColorScheme(
    primary = DarkBlue700,
    surface = DarkBlue800,
    background = DarkBlue800,
    tertiary = Black50,
    secondary = Black300,
    error = Red700
)

@Composable
fun GithubRepositoryFinderTheme(content: @Composable () -> Unit) {
    val colorScheme = ColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            val background = colorScheme.background.toArgb()
            window.setBackgroundDrawable(ColorDrawable(background))
            insetsController.isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}