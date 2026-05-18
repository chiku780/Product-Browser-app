package com.example.domain.repository

import com.example.common.result.ApiResult
import com.example.database.model.ProductListDb
import com.example.domain.model.Product
import com.example.domain.model.ProductList
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    interface Remote {
        suspend fun getAllProducts(): Flow<ApiResult<ProductList>>
        suspend fun productDetails(id: Int): Flow<ApiResult<Product>>
        suspend fun searchProducts(query: String): Flow<ApiResult<ProductList>>
        suspend fun searchProductsByCategory(category: String): Flow<ApiResult<ProductList>>
    }

    interface Local {
        suspend fun saveProduct(list: List<Product>)
        suspend fun getProduct() : Flow<List<Product>>
        suspend fun searchProduct(query: String) : Flow<List<Product>>
    }

}