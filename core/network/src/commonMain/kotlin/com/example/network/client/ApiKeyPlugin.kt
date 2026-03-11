package com.example.network.client

import io.ktor.client.*
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.*
import io.ktor.util.AttributeKey

class ApiKeyPlugin(
    private val apiKey: String,
) : HttpClientPlugin<Unit, ApiKeyPlugin> {

    override val key: AttributeKey<ApiKeyPlugin> = AttributeKey("ApiKeyPlugin")

    override fun prepare(block: Unit.() -> Unit): ApiKeyPlugin = this

    override fun install(plugin: ApiKeyPlugin, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.State) {
            context.headers.append("XApiKey", apiKey)
            proceed()
        }
    }
}