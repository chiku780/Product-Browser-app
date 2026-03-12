package com.example.data.apiService

import com.example.domain.model.allProducts.AllProductResponse
import com.example.domain.model.allProducts.ProductsItem
import com.example.network.client.ProvideHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class ProductsApiService(
    val httpClient: HttpClient
) {
    suspend fun getAllProducts(): Result<AllProductResponse> {
        return try {
            println("Fetching district list...")
            val response = httpClient.get("products")
                .body<AllProductResponse>()
            println("✅ District API response: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun productDetails(id: Int): Result<ProductsItem> {
        return try {
            println("Fetching district list...")
            val response = httpClient.get("products/$id")
                .body<ProductsItem>()
            println("✅ District API response: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchProducts(query: String): Result<AllProductResponse> {
        return try {
            println("Fetching search product list... $query")
            val response = httpClient.get("products/search"){
                parameter("q", query)
            }.body<AllProductResponse>()
            println("✅ District API response: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchProductsByCategory(category: String): Result<AllProductResponse> {
        return try {
            println("Fetching district list...")
            val response = httpClient.get("products"){
                parameter("sortBy", category)
                parameter("order", "asc")
            }
                .body<AllProductResponse>()
            println("✅ District API response: $response")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
