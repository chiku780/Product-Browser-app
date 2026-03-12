package com.example.productbrowserapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.appynitty.ui.colors.CustomColorsConstant
import com.example.common.navigation.NavigationHomeRoute
import com.example.ui.navigation.HomeNavgraph
import com.example.ui.statusBar.SetStatusBarColor
import org.jetbrains.compose.resources.painterResource


@Composable
@Preview
fun App() {
    MaterialTheme {
        val navHostController = rememberNavController()
        SetStatusBarColor(CustomColorsConstant.ColorPrimary, darkIcons = false)
        val bottomPadding = WindowInsets.statusBars.asPaddingValues().calculateBottomPadding()
            NavHost(navHostController, startDestination = NavigationHomeRoute.Root.route) {
                listOf(
                    HomeNavgraph,
                ).forEach {
                    it.build(
                        modifier = Modifier.padding(top = bottomPadding).fillMaxSize(),
                        navHostController = navHostController,
                        navGraphBuilder = this,
                    )
                }
            }
    }
}