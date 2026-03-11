package com.example.domain.useCases

import com.appynitty.common.events.CommonEvents
import com.appynitty.network.base.BaseResult
import com.example.domain.events.homeScreen.HomeScreenEvents
import com.example.domain.repository.ProductRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetAllProductUseCases(private val repository: ProductRepository) {
    operator fun invoke() = flow {
        println("code is working")
        repository.getAllProducts()
            .onStart {
                emit(CommonEvents.IsLoading(true))
            }
            .catch { exception ->
                emit(CommonEvents.IsLoading(false))
                when (exception) {
                    is IOException -> {
                        emit(CommonEvents.ErrorMessage("network_failure"))
                    }
                    else -> {
                        emit(CommonEvents.ErrorMessage("connection_timeout"))
                    }
                }
            }
            .collect { baseResult ->
                emit(CommonEvents.IsLoading(false))
                println("Debug: My message here $baseResult")
                when (baseResult) {
                    is BaseResult.Error -> {
                        emit(CommonEvents.ErrorMessage( "something_went_wrong") )
                    }
                    is BaseResult.Success -> {
                        try {
                            val result = baseResult.data
                            emit(HomeScreenEvents.GetAllProductList(result?.products))
                            println("token is $result")
                        } catch (e: Exception) {
                            emit(CommonEvents.ErrorMessage( "something_went_wrong"))
                        }
                    }

                    is BaseResult.Exception<*> -> {
                        emit(CommonEvents.ErrorMessage(baseResult.message ?: "something_went_wrong"))
                    }
                }
            }
    }
}
