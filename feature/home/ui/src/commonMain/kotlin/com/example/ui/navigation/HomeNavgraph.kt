package com.example.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.navigation.BaseNavGraph
import com.example.common.navigation.NavigationHomeRoute
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.productDetails.ProductDetailsScreen
import com.example.ui.screens.splash.SplashScreen

object HomeNavgraph : BaseNavGraph {

    override fun build(
        modifier: Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
    ) {

        navGraphBuilder.navigation(
            route = NavigationHomeRoute.Root.route,
            startDestination = NavigationHomeRoute.Splash.route
        ) {

            composable(
                route = NavigationHomeRoute.Splash.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                },
            ) {
                SplashScreen(navHostController)
            }

            composable(
                route = NavigationHomeRoute.Home.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                },
            ) {
                HomeScreen(navHostController)
            }

            composable(
                route = NavigationHomeRoute.ProductDetails.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(400)
                    )
                },
            ) {
                ProductDetailsScreen(navHostController)
            }

        }
    }
}