package com.appynitty.common.events

sealed class CommonEvents {
    data class IsLoading(val isLoading: Boolean) : CommonEvents()
    data class ErrorMessage(val errorMessage: String) : CommonEvents()
    data class ShowSuccessMessage(val msg: String?) : CommonEvents()
    data class ShowMessage(val msg: String?) : CommonEvents()
}