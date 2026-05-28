package com.example.ui.sensor.gyroscope

import kotlinx.coroutines.flow.Flow

actual class GyroscopeManager {
    actual fun start() {
    }

    actual fun stop() {
    }

    actual fun observeGyroscope(): kotlinx.coroutines.flow.Flow<com.example.ui.sensor.gyroscope.GyroscopeData> {
        TODO("Not yet implemented")
    }
}

