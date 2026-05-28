package com.example.productbrowserapp.di

import com.example.common.di.iosCommonModule
import com.example.data.di.getHomeDataModule
import com.example.database.di.dataBaseModule
import com.example.database.di.iosDatabaseModule
import com.example.domain.di.getHomeDomainModule
import com.example.network.di.coreNetworkModule
import com.example.ui.di.getHomeUiModule
import com.example.ui.di.getIosUidi
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun initialize() {
        startKoin {
            modules(
                getHomeDomainModule(),
                getHomeDataModule(),
                getHomeUiModule(),
                coreNetworkModule(),
                iosCommonModule,
                dataBaseModule,
                iosDatabaseModule,
                getIosUidi()
            )
        }
    }
}