package com.example.common.connectivityStatus

import kotlinx.coroutines.flow.Flow

expect class ConnectivityStatus   {
     fun isConnected(): Flow<Boolean>
}
