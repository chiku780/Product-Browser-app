package com.example.domain.useCases

import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetAllProductListUseCases(private val repositoryLocal : ProductRepository.Local) {
    operator suspend fun invoke(): Flow<List<Product>>{
        return  repositoryLocal.getProduct()
    }
}