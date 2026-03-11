package com.example.domain.events.homeScreen

import com.example.domain.model.allProducts.ProductsItem

sealed class HomeScreenEvents {
    data class GetAllProductList(val result: List<ProductsItem?>?) : HomeScreenEvents()
    data class OnProductClicked(val id:Int?) : HomeScreenEvents()
}