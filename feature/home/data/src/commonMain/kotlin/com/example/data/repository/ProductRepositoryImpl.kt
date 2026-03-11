package com.example.data.repository

import com.appynitty.network.base.BaseResult
import com.example.data.apiService.ProductsApiService
import com.example.domain.model.allProducts.AllProductResponse
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(private val apiService: ProductsApiService
) : ProductRepository {
    override suspend fun getAllProducts(): Flow<BaseResult<AllProductResponse>> {
        return flow {
            val response = apiService.getAllProducts()
            if(response.isSuccess){
                val body = response.getOrThrow()
                emit(BaseResult.Success(body))
            }else{
                try {
                    emit(BaseResult.Error(response.getOrThrow()!!))
                }catch (e:Exception) {
                    emit(BaseResult.Exception("Something went wrong"))
                }
            }
        }
    }

    override suspend fun productDetails(id: Int): Flow<BaseResult<ProductsItem>> {
        return flow {
            val response = apiService.productDetails(id)
            if(response.isSuccess){
                val body = response.getOrThrow()
                emit(BaseResult.Success(body))
            }else{
                try {
                    emit(BaseResult.Error(response.getOrThrow()!!))
                }catch (e:Exception) {
                    emit(BaseResult.Exception("Something went wrong"))
                }
            }
        }
    }

    override suspend fun searchProducts(query: String): Flow<BaseResult<AllProductResponse>> {
        return flow {
            val response = apiService.searchProducts(query)
            if(response.isSuccess){
                val body = response.getOrThrow()
                emit(BaseResult.Success(body))
            }else{
                try {
                    emit(BaseResult.Error(response.getOrThrow()!!))
                }catch (e:Exception) {
                    emit(BaseResult.Exception("Something went wrong"))
                }
            }
        }
    }

    override suspend fun searchProductsByCategory(category: String): Flow<BaseResult<AllProductResponse>> {
        return flow {
            val response = apiService.searchProducts(category)
            if(response.isSuccess){
                val body = response.getOrThrow()
                emit(BaseResult.Success(body))
            }else{
                try {
                    emit(BaseResult.Error(response.getOrThrow()!!))
                }catch (e:Exception) {
                    emit(BaseResult.Exception("Something went wrong"))
                }
            }
        }
    }



}