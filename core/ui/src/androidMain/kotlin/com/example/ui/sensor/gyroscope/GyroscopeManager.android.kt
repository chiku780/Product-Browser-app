package com.example.ui.sensor.gyroscope

import kotlinx.coroutines.flow.Flow

actual class GyroscopeManager {
    actual fun start() {
    }

    actual fun stop() {
    }

    actual fun observeGyroscope(): Flow<GyroscopeData> {
        TODO("Not yet implemented")
    }
}