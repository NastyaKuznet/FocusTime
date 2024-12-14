package com.example.focustime.di

import androidx.fragment.app.Fragment
import com.example.focustime.presentation.addFriends.AddFriendsFragment
import com.example.focustime.presentation.authorization.AuthorizationFragment
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorFragment
import com.example.focustime.presentation.createTypeIndicator.CreateTypeIndicatorsFragment
import com.example.focustime.presentation.focus.FocusFragment
import com.example.focustime.presentation.indicators.IndicatorsFragment
import com.example.focustime.presentation.newFocus.NewFocusFragment
import com.example.focustime.presentation.openTypeIndicator.OpenTypeIndicatorFragment
import com.example.focustime.presentation.friends.Friend
import com.example.focustime.presentation.friends.FriendsFragment
import com.example.focustime.presentation.registration.RegistrationFragment
import com.example.focustime.presentation.request.RequestFragment

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
    abstract fun inject(fragment: FriendsFragment)
    abstract fun inject(fragment: RequestFragment)
    abstract fun inject(fragment: AddFriendsFragment)
}