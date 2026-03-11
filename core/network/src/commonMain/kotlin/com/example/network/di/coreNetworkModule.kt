package com.example.network.di

import com.example.network.client.ProvideHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun coreNetworkModule() = module {
    single<HttpClient> {ProvideHttpClient().httpClient }
}