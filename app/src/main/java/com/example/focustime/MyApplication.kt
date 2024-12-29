package com.example.focustime

import android.app.Application
import android.content.Context
import com.example.focustime.di.AppComponent
import com.example.focustime.di.DaggerAppComponent

class MyApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        super.onCreate()
    }

}

val Context.appComponent: AppComponent
    get() = when(this) {
        is MyApplication -> this.appComponent
        else -> applicationContext.appComponent
    }