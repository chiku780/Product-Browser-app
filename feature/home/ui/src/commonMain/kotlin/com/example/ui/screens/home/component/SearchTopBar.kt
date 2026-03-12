package com.example.ui.screens.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appynitty.ui.colors.CustomColorsConstant
import com.example.ui.screens.home.HomeScreenUiEvents
import com.example.ui.screens.home.viewModel.HomeScreenViewmodel

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchTopBar(
    query: String,
    viewModel: HomeScreenViewmodel
) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CustomColorsConstant.ColorPrimary,
                titleContentColor = Color.White
            ),
            title = {

                TextField(
                    value = query,
                    onValueChange = {
                        viewModel.onEvents(HomeScreenUiEvents.OnSearchQuery(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 16.dp)
                        .padding(vertical = 10.dp)
                    ,
                    placeholder = { Text("Search products..." ,
                        modifier = Modifier.fillMaxWidth()) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CustomColorsConstant.White,
                        unfocusedContainerColor = CustomColorsConstant.White,
                        disabledContainerColor = CustomColorsConstant.White,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )


            }

        )
    }
