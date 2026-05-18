package com.example.domain.di

import com.example.domain.repository.ProductRepository
import com.example.domain.useCases.GetAllProductListSearchUseCases
import com.example.domain.useCases.GetAllProductListUseCases
import com.example.domain.useCases.GetAllProductUseCases
import com.example.domain.useCases.ProductDetailsUseCases
import com.example.domain.useCases.SearchProductUseCases
import com.example.domain.useCases.SearchProductsByCategoryUseCases
import org.koin.dsl.module

fun getHomeDomainModule() = module {
    factory { GetAllProductUseCases(repository = get<ProductRepository.Remote>(),repositoryLocal = get<ProductRepository.Local>()) }
    factory { SearchProductUseCases(repository = get<ProductRepository.Remote>(),repositoryLocal = get<ProductRepository.Local>()) }
    factory { ProductDetailsUseCases(repository = get<ProductRepository.Remote>()) }
    factory { SearchProductsByCategoryUseCases(repository = get<ProductRepository.Remote>()) }
    factory { GetAllProductListUseCases(repositoryLocal = get<ProductRepository.Local>()) }
    factory { GetAllProductListSearchUseCases(repositoryLocal = get<ProductRepository.Local>()) }
}