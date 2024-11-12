package com.example.focustime.di

import androidx.fragment.app.Fragment
import com.example.focustime.presentation.authorization.AuthorizationFragment
import com.example.focustime.presentation.registration.RegistrationFragment

import dagger.Component

@Component(
    modules =[
        AppBindsModule::class,
        ViewModelModule::class,
        NetworkModule::class,
    ]
)
interface AppComponent {

    abstract fun inject(fragment: RegistrationFragment)
}