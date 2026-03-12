package com.example.ui.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import productbrowserapp.feature.home.ui.generated.resources.Res
import productbrowserapp.feature.home.ui.generated.resources.no_wifi

@Composable
fun NoInternetConnection() {
//    val t = LocalLocalization.current::t
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.no_wifi),
            contentDescription = "No Internet Connection",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "No Internet Connection", fontSize = 20.sp, color = Color.Gray)
    }
}