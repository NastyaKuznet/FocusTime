package com.example.focustime

import android.app.Application
import com.example.focustime.di.AppComponent
import com.example.focustime.di.DaggerAppComponent

class MyApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}