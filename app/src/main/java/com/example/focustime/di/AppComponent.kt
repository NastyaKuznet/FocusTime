package com.example.focustime.di

import androidx.fragment.app.Fragment
import com.example.focustime.presentation.authorization.AuthorizationFragment
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorFragment
import com.example.focustime.presentation.createTypeIndicator.CreateTypeIndicatorsFragment
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
    abstract fun inject(fragment: AuthorizationFragment)
    abstract fun inject(fragment: CreateTypeIndicatorsFragment)
    abstract fun inject(fragment: NewTypeIndicatorFragment)
}