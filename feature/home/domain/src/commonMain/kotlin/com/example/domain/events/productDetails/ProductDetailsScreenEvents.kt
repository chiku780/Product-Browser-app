package com.example.domain.events.productDetails

import com.example.domain.model.allProducts.ProductsItem

sealed class ProductDetailsScreenEvents {
    data class GetProductDetails(val result: List<Pair<String?, String?>>?,val thumbNail: String?,val tittle: String?) : ProductDetailsScreenEvents()
}