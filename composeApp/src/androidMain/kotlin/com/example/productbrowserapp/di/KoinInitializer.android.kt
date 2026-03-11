package com.example.productbrowserapp.di

import android.content.Context
import com.example.data.di.getHomeDataModule
import com.example.domain.di.getHomeDomainModule
import com.example.network.di.coreNetworkModule
import com.example.ui.di.getHomeUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val application: Context,
) {
    actual fun initialize() {
        startKoin {
            androidContext(application)
            modules(
                getHomeDomainModule(),
                getHomeDataModule(),
                getHomeUiModule(),
                coreNetworkModule()
            )
        }
    }
}