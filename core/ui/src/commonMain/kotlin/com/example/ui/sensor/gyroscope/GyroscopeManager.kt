package com.example.ui.sensor.gyroscope

import kotlinx.coroutines.flow.Flow

data class GyroscopeData(
    val x: Float,
    val y: Float,
    val z: Float
)

expect class GyroscopeManager {
    fun start()
    fun stop()

    fun observeGyroscope(): Flow<GyroscopeData>
}