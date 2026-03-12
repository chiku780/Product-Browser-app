package com.example.common.connectivityStatus

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.CoreFoundation.kCFAllocatorDefault
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkFlagsConnectionRequired
import platform.SystemConfiguration.kSCNetworkFlagsReachable

@OptIn(ExperimentalForeignApi::class)
actual class ConnectivityStatus {

    actual fun isConnected(): Flow<Boolean> = callbackFlow {
        memScoped {
//            connectivityStatusListner.getInternetStatus { println("internet status ${it}")
//            if (it)trySend(true) else trySend(false)
//            }


            val reachability = SCNetworkReachabilityCreateWithName(kCFAllocatorDefault, "8.8.8.8")


            if (reachability != null) {
                val flags = alloc<UIntVar>()
                val success = SCNetworkReachabilityGetFlags(reachability, flags.ptr)
                if (success) {
                    val isReachable = (flags.value and kSCNetworkFlagsReachable) != 0u
                    val needsConnection =
                        (flags.value and kSCNetworkFlagsConnectionRequired) != 0u
                    trySend(isReachable && !needsConnection)
                    println("connected")
                } else {
                    trySend(false)
                }
            } else {
                trySend(false)
            }
        }

        awaitClose { /* cleanup if needed */ }
    }
}
