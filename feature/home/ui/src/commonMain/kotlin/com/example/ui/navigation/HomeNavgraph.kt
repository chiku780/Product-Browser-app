package com.example.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.common.utils.animation.sreenTransition.enterTransition
import com.example.common.utils.animation.sreenTransition.exitTransition
import com.example.common.utils.animation.sreenTransition.popEnterTransition
import com.example.common.utils.animation.sreenTransition.popExitTransition
import com.example.common.navigation.BaseNavGraph
import com.example.common.navigation.NavigationHomeRoute
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.productDetails.ProductDetailsScreen
import com.example.ui.screens.splash.SplashScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

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
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) {
                HomeScreen(navHostController)
            }

            composable(
                route = "${NavigationHomeRoute.ProductDetails.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition
            ) { backStackEntry ->
//                val id = backStackEntry.arguments?.getString("id") ?: ""

//                print("here is is $id")
                ProductDetailsScreen(navHostController,"5")
            }

        }
    }
}