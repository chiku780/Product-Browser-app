package com.example.domain.useCases

import com.appynitty.network.base.BaseResult
import com.example.domain.model.allProducts.AllProductResponse
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeErrorRepository : ProductRepository {

    override suspend fun getAllProducts(): Flow<BaseResult<AllProductResponse>> = flow {
        emit(BaseResult.Exception("Something went wrong"))
    }

    override suspend fun productDetails(id: Int) = flow<BaseResult<ProductsItem>> {
        emit(BaseResult.Exception("Something went wrong"))
    }
    override suspend fun searchProducts(query: String) = flow<BaseResult<AllProductResponse>> {
        emit(BaseResult.Exception("Something went wrong"))
    }
    override suspend fun searchProductsByCategory(category: String) = flow<BaseResult<AllProductResponse>> {
        emit(BaseResult.Exception("Something went wrong"))
    }
}