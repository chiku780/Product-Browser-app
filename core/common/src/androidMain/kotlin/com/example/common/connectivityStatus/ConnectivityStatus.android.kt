package com.example.common.connectivityStatus

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


actual class ConnectivityStatus(context: Context) {

    private var connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    actual fun isConnected(): Flow<Boolean> = callbackFlow {
        val TAG = "ConnectivityStatus"

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.i(TAG, "onAvailable")
                trySend(true)
            }

            override fun onLost(network: Network) {
                Log.i(TAG, "onLost")
                trySend(false)
            }

            override fun onUnavailable() {
                Log.i(TAG, "onUnavailable")
                trySend(false)
            }
        }

        @SuppressLint("MissingPermission")
        fun checkInternet() {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                .build()

            // Register the callback
            connectivityManager.registerNetworkCallback(request, networkCallback)

            // Emit initial state
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            val connected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            trySend(connected)
        }

        checkInternet()

        // Cleanup when flow is closed
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}