package com.example.focustime.di

import android.content.Context
import com.example.focustime.MyApplication

val Context.appComponent: AppComponent
    get() = when(this){
        is MyApplication -> appComponent
        else -> applicationContext.appComponent
    }