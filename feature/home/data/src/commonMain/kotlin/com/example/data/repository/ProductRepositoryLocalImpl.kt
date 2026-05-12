package com.example.data.repository

import com.example.database.dao.ProductListDao
import com.example.database.model.ProductListDb
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryLocalImpl(private val productListDao: ProductListDao
) : ProductRepository.Local  {

    override suspend fun saveProduct() {
        productListDao.upsertAllVehicleEntry(listOf(ProductListDb("1","ch","Kl")))
    }

    override suspend fun getProduct(): Flow<List<ProductListDb>> {
        return productListDao.observeVehicleEntryByDate()
    }

}