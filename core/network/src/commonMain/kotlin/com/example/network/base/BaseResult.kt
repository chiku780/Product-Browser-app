package com.appynitty.network.base

sealed class BaseResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T> (data: T?): BaseResult<T>(data)
    class Error<T> (data: T? = null): BaseResult<T>(data)
    class Exception<T>(message: String) : BaseResult<T>(null, message)
}