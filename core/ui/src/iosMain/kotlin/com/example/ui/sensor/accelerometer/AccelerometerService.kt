package com.example.ui.sensor.accelerometer


interface AccelerometerService {
    fun start()
    fun stop()
    fun observeAccelerometer(result :(Float,Float)-> Unit)
}

lateinit var accelerometerService:AccelerometerService