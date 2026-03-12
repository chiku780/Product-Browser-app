package com.example.data.di

import com.example.data.apiService.ProductsApiService
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.ProductRepository
import com.example.network.client.ProvideHttpClient
import org.koin.dsl.module

fun getHomeDataModule() = module {
    single { ProductsApiService(get()) }
    factory<ProductRepository> { ProductRepositoryImpl(apiService = get()) }
}