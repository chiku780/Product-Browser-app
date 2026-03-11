package com.example.ui.screens.productDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.appynitty.ui.library.toast.CustomToast
import com.example.ui.component.appbar.WithBackBar
import com.example.ui.screens.productDetails.component.ProductDetail
import com.example.ui.screens.productDetails.component.ProductDetailRow

@Composable
fun ProductDetailsScreen(navHostController: NavHostController) {
    val productDetails = listOf(
        ProductDetail("Title", "iPhone 15"),
        ProductDetail("Price", "$999"),
        ProductDetail("Brand", "Apple"),
        ProductDetail("Category", "Smartphone"),
        ProductDetail("Rating", "4.8"),
        ProductDetail("Stock", "120"),
        ProductDetail("Discount", "10%"),
        ProductDetail("SKU", "IP15-APL"),
        ProductDetail("Weight", "172g"),
        ProductDetail("Availability", "In Stock")
    )

    Scaffold(
        topBar = {
            WithBackBar("Product Details") {
                navHostController.popBackStack()
            }
        }
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(
                        items = productDetails,
                        key = { it.title }
                    ) { item ->

                        ProductDetailRow(
                            title = item.title,
                            value = item.value
                        )

                    }

                }
            }

            CustomToast()
        }
    }
}