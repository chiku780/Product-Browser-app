package com.example.domain.useCases

import com.appynitty.common.events.CommonEvents
import com.appynitty.network.base.BaseResult
import com.example.domain.events.homeScreen.HomeScreenEvents
import com.example.domain.events.productDetails.ProductDetailsScreenEvents
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.repository.ProductRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


class ProductDetailsUseCases(private val repository: ProductRepository) {
    operator fun invoke(id:Int) = flow {
        println("code is working")
        repository.productDetails(id)
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
                           val prductValue = result?.toKeyValueList()
                            emit(ProductDetailsScreenEvents.GetProductDetails(prductValue,result?.thumbnail,result?.title))
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

    fun ProductsItem.toKeyValueList(): List<Pair<String?, String?>> {
        return listOf(
            "Id" to id.toString(),
            "Title" to title,
            "Description" to description,
            "Category" to category,
            "Price" to price.toString(),
            "Discount %" to discountPercentage.toString(),
            "Rating" to rating.toString(),
            "Stock" to stock.toString(),
            "Brand" to (brand ?: ""),
            "SKU" to sku,
            "Weight" to weight.toString(),
            "Warranty" to warrantyInformation,
            "Shipping" to shippingInformation,
            "Availability" to availabilityStatus,
            "Return Policy" to returnPolicy,
            "Minimum Order" to minimumOrderQuantity.toString()
        )
    }
}
