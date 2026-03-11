package com.example.productbrowserapp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.productbrowserapp.di.KoinInitializer

fun MainViewController() = ComposeUIViewController( configure = {
    KoinInitializer().initialize()
}) {
    App()
}