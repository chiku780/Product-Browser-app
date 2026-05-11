package com.example.ui.screens.home

import com.example.domain.model.Product

data class HomeScreenUiState(
    val showLoading : Boolean = false,
    val productList: List<Product> = emptyList(),
    )