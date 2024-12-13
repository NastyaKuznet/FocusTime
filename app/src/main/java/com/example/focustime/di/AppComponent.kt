package com.example.focustime.di

import com.example.focustime.presentation.authorization.AuthorizationFragment
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorFragment
import com.example.focustime.presentation.createTypeIndicator.CreateTypeIndicatorsFragment
import com.example.focustime.presentation.focus.FocusFragment
import com.example.focustime.presentation.indicators.IndicatorsFragment
import com.example.focustime.presentation.newFocus.NewFocusFragment
import com.example.focustime.presentation.openTypeIndicator.OpenTypeIndicatorFragment
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
    fun inject(fragment: OpenTypeIndicatorFragment)
    fun inject(fragment: FocusFragment)
    fun inject(fragment: NewFocusFragment)
    fun inject(fragment: IndicatorsFragment)
}