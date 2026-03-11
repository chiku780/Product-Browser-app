package com.example.productbrowserapp

import android.app.Application
import com.example.productbrowserapp.di.KoinInitializer

class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).initialize()
    }
}