package com.example.common.result

sealed class BaseResult<out T> {
    data class Success<T>(val data: T) : BaseResult<T>()
    data class Error(val exception: Throwable) : BaseResult<Nothing>()
    data class Loading(val show: Boolean) : BaseResult<Nothing>()
    data class UiError(val msg: String) : BaseResult<Nothing>()
    data class UiSuccess(val msg: String) : BaseResult<Nothing>()
    data class UiWarning(val msg: String) : BaseResult<Nothing>()
}

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable) : ApiResult<Nothing>()
}


fun <T> Result<T>.toDataResult(): ApiResult<T> {
    return fold(
        onSuccess = { ApiResult.Success(it) },
        onFailure = { ApiResult.Error(it) }
    )
}