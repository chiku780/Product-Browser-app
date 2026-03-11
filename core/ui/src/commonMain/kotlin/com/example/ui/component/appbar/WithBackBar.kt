package com.example.ui.component.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appynitty.ui.colors.CustomColorsConstant
import com.appynitty.ui.component.text.CommonText
import org.jetbrains.compose.resources.painterResource
import productbrowserapp.core.ui.generated.resources.Res
import productbrowserapp.core.ui.generated.resources.ic_arrow_back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithBackBar(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomColorsConstant.ColorPrimary,
            titleContentColor = Color.White
        ),
        title = {
            CommonText(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color.White,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick, Modifier.padding(start = 12.dp).size(30.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = "map back icon",
                    tint = Color.White
                )
            }
        }
    )
}