package com.example.ui.events.flowHandle



import com.example.common.result.BaseResult
import com.example.ui.events.MultiLangMessage
import com.example.ui.events.toAppErrorMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<BaseResult<T>>.collectWithUiHandling(
    scope: CoroutineScope,
    onLoading: (Boolean) -> Unit,
    onSuccess: suspend (T?) -> Unit = {},
    uiError: suspend (String) -> Unit = {},
    uiWarning: suspend (String) -> Unit = {},
    uiSuccess: suspend (String) -> Unit = {},
) {
    scope.launch {
        collect { result ->
            when (result) {
                is BaseResult.Loading -> onLoading(result.show)

                is BaseResult.Error -> {
                    onLoading(false)
                    val message = result.exception?.toAppErrorMessage() ?: "Unexpected Error"
                    uiError(message)
                }

                is BaseResult.Success -> {
                    onLoading(false)
                    onSuccess(result.data)
                }

                is BaseResult.UiError -> {
                    uiError(result.msg)
                }

                is BaseResult.UiSuccess -> {
                    uiSuccess(result.msg)
                }
                is BaseResult.UiWarning -> {
                    uiWarning(result.msg)
                }
            }
        }
    }
}