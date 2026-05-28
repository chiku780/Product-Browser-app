package com.example.ui.sensor.accelerometer

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


actual class AccelerometerManager {
   actual fun start(){
    accelerometerService.start()
    }
    actual fun stop() {
     accelerometerService.stop()
    }

    actual fun observeAccelerometer(): Flow<GravityData> = callbackFlow {

     accelerometerService.observeAccelerometer { x, y ->

      trySend(
       GravityData(
        x = x,
        y = y
       )
      )
     }

     awaitClose {
      accelerometerService.stop()
     }
    }
}