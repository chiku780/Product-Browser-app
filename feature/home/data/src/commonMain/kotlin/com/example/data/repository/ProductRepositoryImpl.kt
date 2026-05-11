package com.example.data.repository

import com.example.common.result.ApiResult
import com.example.data.apiService.ProductsApiService
import com.example.data.mappers.toDomain
import com.example.domain.model.Product
import com.example.domain.model.ProductList
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(private val apiService: ProductsApiService
) : ProductRepository.Remote {

    override suspend fun getAllProducts():Flow<ApiResult<ProductList>> {
        return flow {
            val response = apiService.getAllProducts()
            response.fold(
                onSuccess = { dto ->
                    emit(ApiResult.Success(dto.toDomain()))
                },
                onFailure = {
                    emit(ApiResult.Error(it))
                }
            )
        }
    }

    override suspend fun productDetails(id: Int): Flow<ApiResult<Product>> {
        return flow {
            val response = apiService.productDetails(id)
            response.fold(
                onSuccess = { dto ->
                    emit(ApiResult.Success(dto.toDomain()))
                },
                onFailure = {
                    emit(ApiResult.Error(it))
                }
            )
        }
    }

    override suspend fun searchProducts(query: String): Flow<ApiResult<ProductList>> {
        return flow {
            val response = apiService.searchProducts(query)
            response.fold(
                onSuccess = { dto ->
                    emit(ApiResult.Success(dto.toDomain()))
                },
                onFailure = {
                    emit(ApiResult.Error(it))
                }
            )
        }
    }

    override suspend fun searchProductsByCategory(category: String): Flow<ApiResult<ProductList>> {
        return flow {
            val response = apiService.searchProducts(category)
            response.fold(
                onSuccess = { dto ->
                    emit(ApiResult.Success(dto.toDomain()))
                },
                onFailure = {
                    emit(ApiResult.Error(it))
                }
            )
        }
    }

}