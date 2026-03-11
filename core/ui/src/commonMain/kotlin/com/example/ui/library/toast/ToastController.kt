package com.appynitty.ui.library.toast

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object ToastController {
    var message by mutableStateOf<String?>(null)
        private set
    var type by mutableStateOf(ToastType.INFO)
        private set

    fun show(message: String, type: ToastType = ToastType.INFO) {
        this.message = message
        this.type = type
    }

    fun clear() {
        message = null
    }
}