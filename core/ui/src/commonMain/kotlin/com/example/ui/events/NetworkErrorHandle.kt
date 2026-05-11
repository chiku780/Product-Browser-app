package com.example.ui.events

import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.TimeoutCancellationException


fun Throwable.toAppErrorMessage(): String {
    return when (this) {
        is IOException -> "No Internet Connection"
        is TimeoutCancellationException -> "Connection Timeout"
        else -> "Unexpected Error"
    }
}