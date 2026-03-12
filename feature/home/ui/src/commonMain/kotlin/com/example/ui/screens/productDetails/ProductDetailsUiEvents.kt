package com.example.ui.screens.productDetails

import com.example.ui.screens.home.HomeScreenUiEvents

sealed class ProductDetailsUiEvents {
    data class ProductDetailsApiCall(val id:Int?) : ProductDetailsUiEvents()
    data class SwipeRefresh(val id:Int?) : ProductDetailsUiEvents()
}