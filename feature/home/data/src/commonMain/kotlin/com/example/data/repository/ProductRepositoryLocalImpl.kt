package com.example.data.repository

import com.example.data.mappers.toDomain
import com.example.data.mappers.toProductListDb
import com.example.database.dao.ProductListDao
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryLocalImpl(private val productListDao: ProductListDao
) : ProductRepository.Local  {

    override suspend fun saveProduct(list: List<Product>) {
        val products = list.map { it.toProductListDb() }
        productListDao.insertProductList(products)
    }

    override suspend fun getProduct(): Flow<List<Product>> {
        return productListDao
            .getAllProduct()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun searchProduct(query: String): Flow<List<Product>> {
        return productListDao
            .getProductBySearch(query)
            .map { list ->
                list.map { it.toDomain() }
            }
    }

}