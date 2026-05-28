package com.example.ui.sensor.accelerometer

import kotlinx.coroutines.flow.Flow


data class GravityData(
    val x: Float,
    val y: Float
)

expect class AccelerometerManager {
    fun start()
    fun stop()

    fun observeAccelerometer(): Flow<GravityData>
}