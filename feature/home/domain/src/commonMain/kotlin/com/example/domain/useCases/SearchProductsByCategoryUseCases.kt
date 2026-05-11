package com.example.domain.useCases

import com.example.common.result.ApiResult
import com.example.common.result.BaseResult
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


class SearchProductsByCategoryUseCases(private val repository: ProductRepository.Remote) {
    operator fun invoke(category: String) = flow {
        println("code is working")
        repository.searchProductsByCategory(category)
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
//                            emit(HomeScreenEvents.GetAllProductList(result?.products))
                            println("token is $result")
                        } catch (e: Exception) {
                            emit(BaseResult.Error(e ))
                        }
                    }

                }
            }
    }
}
