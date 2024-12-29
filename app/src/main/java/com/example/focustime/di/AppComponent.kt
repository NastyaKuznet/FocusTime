package com.example.focustime.di

import com.example.focustime.presentation.sendRequest.SendRequestFragment
import com.example.focustime.presentation.authorization.AuthorizationFragment
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorFragment
import com.example.focustime.presentation.indicators.IndicatorsFragment
import com.example.focustime.presentation.focus.FocusFragment
import com.example.focustime.presentation.history.HistoryFragment
import com.example.focustime.presentation.newFocus.NewFocusFragment
import com.example.focustime.presentation.openTypeIndicator.OpenTypeIndicatorFragment
import com.example.focustime.presentation.friends.FriendsFragment
import com.example.focustime.presentation.registration.RegistrationFragment
import com.example.focustime.presentation.acceptRequest.AcceptRequestFragment
import com.example.focustime.presentation.accountUser.AccountUserEditFragment
import com.example.focustime.presentation.accountUser.AccountUserFragment
import com.example.focustime.presentation.avatar.AvatarFragment

import dagger.Component

@Component(
    modules =[
        AppBindsModule::class,
        ViewModelModule::class,
        NetworkModule::class,
    ]
)
interface AppComponent {

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: IndicatorsFragment)
    fun inject(fragment: NewTypeIndicatorFragment)
    fun inject(fragment: OpenTypeIndicatorFragment)
    fun inject(fragment: FocusFragment)
    fun inject(fragment: NewFocusFragment)
    fun inject(fragment: HistoryFragment)
    fun inject(fragment: FriendsFragment)
    fun inject(fragment: AcceptRequestFragment)
    fun inject(fragment: SendRequestFragment)
    fun inject(fragment: AccountUserFragment)
    fun inject(fragment: AccountUserEditFragment)
    fun inject(fragment: AvatarFragment)
}