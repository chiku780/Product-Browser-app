package com.example.domain.repository

import com.appynitty.network.base.BaseResult
import com.example.domain.model.allProducts.AllProductResponse
import com.example.domain.model.allProducts.ProductsItem
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(): Flow<BaseResult<AllProductResponse>>
    suspend fun productDetails(id: Int): Flow<BaseResult<ProductsItem>>
    suspend fun searchProducts(query: String): Flow<BaseResult<AllProductResponse>>
    suspend fun searchProductsByCategory(category: String): Flow<BaseResult<AllProductResponse>>
}