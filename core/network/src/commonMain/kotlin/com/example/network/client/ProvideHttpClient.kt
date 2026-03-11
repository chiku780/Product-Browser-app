package com.example.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json




class ProvideHttpClient {


    val httpClient: HttpClient by lazy {
        HttpClient {
            println("block running")
//            install(ApiKeyPlugin(apiKey = "c3188948-0de5-4649-96a3-e1ad3593e30f"))
            install(ContentNegotiation) {
                json(json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(DefaultRequest) {
                runBlocking {
                    val baseUrl = "dummyjson.com"
                    url {
                        host = baseUrl
                        protocol = URLProtocol.HTTPS
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 300000
                connectTimeoutMillis = 300000
                requestTimeoutMillis = 300000
            }

        }
    }
}
