package com.example.domain.useCases

import com.appynitty.network.base.BaseResult
import com.example.domain.model.allProducts.AllProductResponse
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSuccessProductRepository : ProductRepository {

    override suspend fun getAllProducts() = flow {
        val fakeProducts = listOf(
            ProductsItem(
                id = 1,
                title = "Phone",
                price = 500.0
            ),
            ProductsItem(
                id = 2,
                title = "Laptop",
                price = 1000.0
            )
        )

        emit(
            BaseResult.Success(
                AllProductResponse(
                    total = 2,
                    products = fakeProducts
                )
            )
        )
    }

    override suspend fun productDetails(id: Int): Flow<BaseResult<ProductsItem>> = flow {
        emit(BaseResult.Success(ProductsItem(id = id, title = "Phone")))
    }

    override suspend fun searchProducts(query: String): Flow<BaseResult<AllProductResponse>> = flow {
        emit(BaseResult.Success(AllProductResponse(products = emptyList())))
    }

    override suspend fun searchProductsByCategory(category: String): Flow<BaseResult<AllProductResponse>> = flow {
        emit(BaseResult.Success(AllProductResponse(products = emptyList())))
    }
}