package com.example.ui.di

import com.example.domain.useCases.GetAllProductUseCases
import com.example.domain.useCases.ProductDetailsUseCases
import com.example.domain.useCases.SearchProductUseCases
import com.example.ui.screens.home.viewModel.HomeScreenViewmodel
import com.example.ui.screens.productDetails.viewModel.ProductDetailsViewModel
import com.example.ui.screens.share.NavArgsShare
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getHomeUiModule() = module {
    viewModel {
        HomeScreenViewmodel(
            getAllProductUseCases = get<GetAllProductUseCases>(),
            searchProductUseCases = get<SearchProductUseCases>(),
            connectivityStatus = get(),
            navArgsShare = get ()
        )
    }
    viewModel {
        ProductDetailsViewModel(
            productDetailsUseCases = get<ProductDetailsUseCases>(),
            connectivityStatus = get(),
            navArgsShare = get ()
        )
    }
    single { NavArgsShare() }
}