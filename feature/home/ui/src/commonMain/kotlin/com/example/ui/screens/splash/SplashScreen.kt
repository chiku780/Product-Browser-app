package com.example.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.appynitty.ui.colors.CustomColorsConstant
import com.example.common.navigation.NavigationHomeRoute
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import productbrowserapp.feature.home.ui.generated.resources.Res
import productbrowserapp.feature.home.ui.generated.resources.product

@Composable
fun SplashScreen(navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(1000)
        navHostController.navigate(NavigationHomeRoute.Home.route){
            popUpTo(NavigationHomeRoute.Splash.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(CustomColorsConstant.ColorPrimary)){
        Image(
            painter = painterResource(Res.drawable.product),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center).size(160.dp),
            contentScale = ContentScale.Crop
        )
    }
}