package com.example.domain.useCases

import com.example.common.result.ApiResult
import com.example.common.result.BaseResult
import com.example.domain.events.productDetails.GetProductDetails
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


class ProductDetailsUseCases(private val repository: ProductRepository.Remote) {
    operator fun invoke(id:Int) = flow {

        println("code is working")
        repository.productDetails(id)
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
                           val prductValue = result?.toKeyValueList()
//                            emit(ProductDetailsScreenEvents.GetProductDetails(prductValue,result?.thumbnail,result?.title))

                            emit(BaseResult.Success(
                                GetProductDetails(
                                    prductValue, result?.thumbnail, result?.title
                                )
                            ))
                            println("token is $result")
                        } catch (e: Exception) {
                            emit(BaseResult.Error(e ))
                        }
                    }
                }
            }
    }

    fun Product.toKeyValueList(): List<Pair<String?, String?>> {
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
