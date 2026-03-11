package com.example.ui.statusBar

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val view = LocalView.current
    LaunchedEffect(Unit) {
        val window = (view.context as Activity).window

        window.statusBarColor = color.toArgb()

        // Light or dark icons
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = darkIcons
    }
}