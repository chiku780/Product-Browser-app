package com.example.ui.sensor.gyroscope

interface GyroScopeService {
    fun start()
    fun stop()
    fun observeGyro(result :(Float,Float,Float)-> Unit)
}

lateinit var gyroScopeService:GyroScopeService