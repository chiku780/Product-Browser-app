package com.example.domain.useCases

import com.appynitty.network.base.BaseResult
import com.example.domain.model.allProducts.AllProductResponse
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBaseErrorRepository : ProductRepository {

    override suspend fun getAllProducts(): Flow<BaseResult<AllProductResponse>> = flow {
        emit(BaseResult.Error<AllProductResponse>())
    }

    override suspend fun productDetails(id: Int) = flow<BaseResult<ProductsItem>> {
        emit(BaseResult.Error<ProductsItem>())
    }
    override suspend fun searchProducts(query: String) = flow<BaseResult<AllProductResponse>> {
        emit(BaseResult.Error<AllProductResponse>())
    }
    override suspend fun searchProductsByCategory(category: String) = flow<BaseResult<AllProductResponse>> {
        emit(BaseResult.Error<AllProductResponse>())
    }
}
