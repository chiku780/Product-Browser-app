package com.example.data.mappers

import com.example.data.model.AllProductResponse
import com.example.data.model.ProductsItem
import com.example.data.model.ReviewsItem
import com.example.domain.model.Product
import com.example.domain.model.ProductList
import com.example.domain.model.Review

fun AllProductResponse.toDomain(): ProductList {
    return ProductList(
        total = total ?: 0,
        limit = limit ?: 0,
        skip = skip ?: 0,
        products = products
            ?.filterNotNull()
            ?.map { it.toDomain() }
            ?: emptyList()
    )
}

fun ProductsItem.toDomain(): Product {
    return Product(
        id = id ?: 0,
        title = title.orEmpty(),
        description = description.orEmpty(),
        category = category.orEmpty(),
        price = price ?: 0.0,
        discountPercentage = discountPercentage ?: 0.0,
        rating = rating ?: 0.0,
        stock = stock ?: 0,
        tags = tags?.filterNotNull() ?: emptyList(),
        brand = brand.orEmpty(),
        sku = sku.orEmpty(),
        weight = weight ?: 0.0,
        warrantyInformation = warrantyInformation.orEmpty(),
        shippingInformation = shippingInformation.orEmpty(),
        availabilityStatus = availabilityStatus.orEmpty(),
        returnPolicy = returnPolicy.orEmpty(),
        minimumOrderQuantity = minimumOrderQuantity ?: 0,
        thumbnail = thumbnail.orEmpty(),
        images = images?.filterNotNull() ?: emptyList(),
        reviews = reviews
            ?.filterNotNull()
            ?.map { it.toDomain() }
            ?: emptyList()
    )
}

fun ReviewsItem.toDomain(): Review {
    return Review(
        rating = rating ?: 0,
        comment = comment.orEmpty(),
        date = date.orEmpty(),
        reviewerName = reviewerName.orEmpty(),
        reviewerEmail = reviewerEmail.orEmpty()
    )
}