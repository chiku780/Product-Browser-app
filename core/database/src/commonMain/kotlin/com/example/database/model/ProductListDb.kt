package com.example.database.model

data class ProductListDb( val id: Long,
                          val title: String,
                          val description: String,
                          val category: String,
                          val price: Double,
                          val discountPercentage: Double,
                          val rating: Double,
                          val stock: Long,
                          val brand: String,
                          val sku: String,
                          val weight: Double,
                          val warrantyInformation: String,
                          val shippingInformation: String,
                          val availabilityStatus: String,
                          val returnPolicy: String,
                          val minimumOrderQuantity: Long,
                          val thumbnail: String)
