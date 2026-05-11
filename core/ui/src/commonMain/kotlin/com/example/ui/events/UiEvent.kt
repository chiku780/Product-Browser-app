package com.example.ui.events



data class MultiLangMessage(
    val en: String? = null,
    val hi: String? = null,
    val mr: String? = null
)

sealed class UiEvent {
    data class ShowUiError(val message: String) : UiEvent()
    data class ShowUiWarning(val message: String) : UiEvent()
    data class ShowUiSuccess(val message: String) : UiEvent()
}