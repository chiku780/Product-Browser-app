package com.example.ui.di

import com.example.domain.useCases.GetAllProductUseCases
import com.example.ui.screens.home.viewModel.HomeScreenViewmodel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getHomeUiModule() = module {
    viewModel {
        HomeScreenViewmodel(
            getAllProductUseCases = get<GetAllProductUseCases>()
        )
    }
}