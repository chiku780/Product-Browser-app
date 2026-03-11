package com.example.productbrowserapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform