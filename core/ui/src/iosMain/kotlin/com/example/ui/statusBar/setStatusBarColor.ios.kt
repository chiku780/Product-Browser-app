package com.example.ui.statusBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIView
import platform.UIKit.statusBarManager

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    LaunchedEffect(Unit) {
        val uiColor = UIColor(
            red = color.red.toDouble(),
            green = color.green.toDouble(),
            blue = color.blue.toDouble(),
            alpha = color.alpha.toDouble()
        )

        val window = UIApplication.sharedApplication.keyWindow
        window?.let {
            val frame = it.windowScene?.statusBarManager?.statusBarFrame ?: return@LaunchedEffect
            val statusBarView = UIView(frame = frame)
            statusBarView.backgroundColor = uiColor
            it.addSubview(statusBarView)
        }
    }
}
