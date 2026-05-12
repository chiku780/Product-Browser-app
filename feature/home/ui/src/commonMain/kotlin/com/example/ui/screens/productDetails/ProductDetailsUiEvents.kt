package com.example.ui.screens.productDetails

import com.example.ui.screens.home.HomeScreenUiEvents

sealed class ProductDetailsUiEvents {
    data object Init : ProductDetailsUiEvents()
    data object SwipeRefresh : ProductDetailsUiEvents()
}