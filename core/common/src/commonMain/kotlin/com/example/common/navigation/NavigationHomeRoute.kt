package com.example.common.navigation

sealed class NavigationHomeRoute(val route: String) {
    data object Root : NavigationHomeRoute("/home_root")
    data object Home : NavigationHomeRoute("/home")
    data object Splash : NavigationHomeRoute("/splash")
    data object ProductDetails : NavigationHomeRoute("/product_details")
}