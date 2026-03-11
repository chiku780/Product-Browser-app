package com.appynitty.ui.library.toast

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import productbrowserapp.core.ui.generated.resources.Res
import productbrowserapp.core.ui.generated.resources.ic_check_24
import productbrowserapp.core.ui.generated.resources.ic_close_24
import productbrowserapp.core.ui.generated.resources.ic_info_outline_24
import productbrowserapp.core.ui.generated.resources.ic_warning_24

@Composable
@Preview
fun CustomToast() {

    val message = ToastController.message
    val type = ToastController.type

    if (message != null) {
        val bgColor = when (type) {
            ToastType.SUCCESS -> Color(0xFF4CAF50) // Green
            ToastType.ERROR -> Color(0xFFF44336)   // Red
            ToastType.WARNING -> Color(0xFFFFC107) // Yellow
            ToastType.INFO -> Color(0xFF323232)    // Gray
        }

        val iconType = when (type) {
            ToastType.SUCCESS -> Res.drawable.ic_check_24
            ToastType.ERROR ->  Res.drawable.ic_close_24
            ToastType.WARNING ->  Res.drawable.ic_warning_24
            ToastType.INFO ->  Res.drawable.ic_info_outline_24
        }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 80.dp, start = 16.dp, end = 16.dp)
                        .shadow(8.dp, RoundedCornerShape(32.dp))
                        .background(bgColor, RoundedCornerShape(32.dp))
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(iconType),
                        modifier = Modifier.size(20.dp),
                        tint = Color.White,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = message,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

        LaunchedEffect(message) {
            delay(2000)
            ToastController.clear()
        }
    }


}