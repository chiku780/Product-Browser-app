package com.example.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.appynitty.ui.library.toast.ToastController
import com.appynitty.ui.library.toast.ToastType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun HandleUiEvents(
    eventFlow: Flow<UiEvent>,
) {

    LaunchedEffect(Unit) {
        launch {
            eventFlow.collect { event ->
                when (event) {

                    is UiEvent.ShowUiError -> {
                        ToastController.show(event.message, ToastType.ERROR)
                    }

                    is UiEvent.ShowUiSuccess -> {
                        ToastController.show(event.message, ToastType.SUCCESS)
                    }

                    is UiEvent.ShowUiWarning -> {
                        ToastController.show(event.message, ToastType.WARNING)
                    }

                }
            }
        }
    }


}