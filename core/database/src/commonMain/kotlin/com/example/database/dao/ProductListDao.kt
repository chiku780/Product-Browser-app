package com.example.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.core.database.AppDatabase
import com.example.database.model.ProductListDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductListDao(
    private val db: AppDatabase
) {

    private val productListTableQuery = db.productListTableQueries

    fun upsertAllVehicleEntry(list: List<ProductListDb>) {
        productListTableQuery.transaction {
            list.forEach {
                productListTableQuery.insertProduct(
                    id = it.id,
                    name = it.name,
                    role = it.role
                )
            }
        }
    }

    fun observeVehicleEntryByDate(): Flow<List<ProductListDb>> {
        return productListTableQuery
            .getAllProduct()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map {
                    ProductListDb(
                        id = it.id,
                        name = it.name.orEmpty(),
                        role = it.role.orEmpty()
                    )
                }
            }
    }

    fun removeAllVehicleEntry(){
        productListTableQuery.removeAllProduct()
    }

}