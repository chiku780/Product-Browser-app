package com.example.ui.di

import com.example.ui.sensor.accelerometer.AccelerometerManager
import com.example.ui.sensor.gyroscope.GyroscopeManager
import org.koin.dsl.module

fun getIosUidi()  = module {
    single { GyroscopeManager() }
    single { AccelerometerManager() }
}