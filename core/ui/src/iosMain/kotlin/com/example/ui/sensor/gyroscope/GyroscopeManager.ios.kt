package com.example.ui.sensor.gyroscope

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


actual class GyroscopeManager {

    actual fun start() {
        gyroScopeService.start()
    }

    actual fun stop() {
        gyroScopeService.stop()
    }

    actual fun observeGyroscope(): Flow<GyroscopeData> = callbackFlow {
        gyroScopeService.observeGyro { x, y, z ->

            trySend(
                GyroscopeData(
                    x = x,
                    y = y,
                    z = z
                )
            )
        }

        awaitClose {
            gyroScopeService.stop()
        }
    }
}