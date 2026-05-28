package com.example.ui.screens.ballPhysics.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ui.screens.ballPhysics.BallUiState
import com.example.ui.sensor.accelerometer.AccelerometerManager
import com.example.ui.sensor.gyroscope.GyroscopeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BallScreenViewmodel(private val gyroscopeManager: GyroscopeManager,
                          private val accelerometerManager: AccelerometerManager
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(BallUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        startGyro()
        observeGyro()
    }

   val gravityX = 0f
   val  gravityY = 1200f

    private fun startGyro(){
//        gyroscopeManager.start()
        accelerometerManager.start()
    }

    private fun observeGyro(){
        viewModelScope.launch {
//            gyroscopeManager.observeGyroscope().collect {
//                println("here is the gyro data $it")
//            }
            accelerometerManager
                .observeAccelerometer()
                .collect { data ->
                    _uiState.update {
                        it.copy(
                            gravityX = data.x * 1800f,
                            gravityY = data.y * 1800f
                        )
                    }
                }
        }
    }

    private fun stopGyro(){
        gyroscopeManager.stop()
    }
}