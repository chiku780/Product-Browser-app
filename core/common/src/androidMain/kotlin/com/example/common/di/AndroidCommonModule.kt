package com.example.common.di

import android.content.Context
import com.example.common.connectivityStatus.ConnectivityStatus
import org.koin.dsl.module

val androidCommonModule = module {
    single {
        val context: Context = get()
        ConnectivityStatus(context)
    }
}