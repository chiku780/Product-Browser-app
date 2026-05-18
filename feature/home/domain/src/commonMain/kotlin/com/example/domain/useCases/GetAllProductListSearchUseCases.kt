package com.example.domain.useCases

import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetAllProductListSearchUseCases(private val repositoryLocal : ProductRepository.Local) {
    operator suspend fun invoke(query: String): Flow<List<Product>>{
        return  repositoryLocal.searchProduct(query)
    }
}