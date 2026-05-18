package com.example.domain.useCases

import com.example.common.result.ApiResult
import com.example.common.result.BaseResult
import com.example.domain.events.homeScreen.AllProductList
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetAllProductUseCases(private val repository: ProductRepository.Remote,
                            private val repositoryLocal: ProductRepository.Local) {
    operator fun invoke() = flow {
        println("code is working")
        repository.getAllProducts()
            .onStart {
                emit(BaseResult.Loading(true))
            }
            .catch { exception ->
                emit(BaseResult.Loading(false))
                emit(BaseResult.Error(exception ))
            }
            .collect { baseResult ->
                emit(BaseResult.Loading(false))
                println("Debug: My message here $baseResult")
                when (baseResult) {
                    is ApiResult.Error -> {
                        emit(BaseResult.Error(baseResult.exception ))
                    }
                    is ApiResult.Success -> {
                        try {
                            val result = baseResult.data
                            repositoryLocal.saveProduct(result.products)
                        } catch (e: Exception) {
                            emit(BaseResult.Error(e ))
                        }
                    }

                }
            }
    }
}
