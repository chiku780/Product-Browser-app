package com.example.ui.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTop(route: String) {
    this.navigate(route) {
        launchSingleTop = true          // Prevent duplicate destinations
        restoreState = true             // Restore previous state (if exists)
    }
}