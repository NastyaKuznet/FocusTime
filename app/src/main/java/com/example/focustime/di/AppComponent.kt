package com.example.focustime.di

import androidx.fragment.app.Fragment

import dagger.Component

@Component(
    modules =[
        AppBindsModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject(fragment: MainInterconfFragment)
}