package com.example.productbrowserapp.di

import com.example.data.di.getHomeDataModule
import com.example.domain.di.getHomeDomainModule
import com.example.network.di.coreNetworkModule
import com.example.ui.di.getHomeUiModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun initialize() {
        startKoin {
            modules(
                getHomeDomainModule(),
                getHomeDataModule(),
                getHomeUiModule(),
                coreNetworkModule()
            )
        }
    }
}