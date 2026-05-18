package com.example.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
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

    fun insertProductList(list: List<ProductListDb>) {
        productListTableQuery.transaction {
            list.forEach {
                productListTableQuery.insertProduct(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    category = it.category,
                    price = it.price,
                    discountPercentage = it.discountPercentage,
                    rating = it.rating,
                    stock = it.stock,
                    brand = it.brand,
                    sku = it.sku,
                    weight = it.weight,
                    warrantyInformation = it.warrantyInformation,
                    shippingInformation = it.shippingInformation,
                    availabilityStatus = it.availabilityStatus,
                    returnPolicy = it.returnPolicy,
                    minimumOrderQuantity = it.minimumOrderQuantity,
                    thumbnail = it.thumbnail
                )
            }
        }
    }

    fun getAllProduct(): Flow<List<ProductListDb>> {
        return productListTableQuery
            .getAllProduct()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map {
                    ProductListDb(
                        id = it.id,
                        title = it.title.orEmpty(),
                        description = it.description.orEmpty(),
                        category = it.category.orEmpty(),
                        price = it.price ?: 0.0,
                        discountPercentage = it.discountPercentage ?: 0.0,
                        rating = it.rating ?: 0.0,
                        stock = it.stock ?: 0,
                        brand = it.brand.orEmpty(),
                        sku = it.sku.orEmpty(),
                        weight = it.weight ?: 0.0,
                        warrantyInformation = it.warrantyInformation.orEmpty(),
                        shippingInformation = it.shippingInformation.orEmpty(),
                        availabilityStatus = it.availabilityStatus.orEmpty(),
                        returnPolicy = it.returnPolicy.orEmpty(),
                        minimumOrderQuantity = it.minimumOrderQuantity ?: 0,
                        thumbnail = it.thumbnail.orEmpty()
                    )
                }
            }
    }

    fun getProductBySearch(search : String): Flow<List<ProductListDb>> {
        return productListTableQuery
            .searchProducts(search)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map {
                    ProductListDb(
                        id = it.id,
                        title = it.title.orEmpty(),
                        description = it.description.orEmpty(),
                        category = it.category.orEmpty(),
                        price = it.price ?: 0.0,
                        discountPercentage = it.discountPercentage ?: 0.0,
                        rating = it.rating ?: 0.0,
                        stock = it.stock ?: 0,
                        brand = it.brand.orEmpty(),
                        sku = it.sku.orEmpty(),
                        weight = it.weight ?: 0.0,
                        warrantyInformation = it.warrantyInformation.orEmpty(),
                        shippingInformation = it.shippingInformation.orEmpty(),
                        availabilityStatus = it.availabilityStatus.orEmpty(),
                        returnPolicy = it.returnPolicy.orEmpty(),
                        minimumOrderQuantity = it.minimumOrderQuantity ?: 0,
                        thumbnail = it.thumbnail.orEmpty()
                    )
                }
            }
    }

//    fun getProductById(id: Long) : ProductListDb {
//        return  productListTableQuery
//            .getProductById(id)
//
//    }


    fun deleteAllProduct(){
        productListTableQuery.removeAllProduct()
    }

}