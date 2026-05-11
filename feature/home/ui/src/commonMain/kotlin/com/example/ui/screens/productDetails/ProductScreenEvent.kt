package com.example.ui.screens.productDetails

sealed class ProductScreenEvent {
    data class OnProductClicked(val id:Int?) : ProductScreenEvent()
}