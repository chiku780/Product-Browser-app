package com.example.common.di

import com.example.common.connectivityStatus.ConnectivityStatus
import org.koin.dsl.module

val iosCommonModule = module {
    single {
        ConnectivityStatus()
    }
}