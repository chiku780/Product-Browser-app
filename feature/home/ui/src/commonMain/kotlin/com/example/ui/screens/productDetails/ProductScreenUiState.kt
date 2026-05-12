package com.example.ui.screens.productDetails


data class ProductScreenUiState(
    val showLoading : Boolean = false,
    val productDetails: List<Pair<String?, String?>>? = emptyList(),
    val thumbnail : String = "",
    val title : String = "",
)