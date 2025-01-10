package com.example.focustime.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focustime.presentation.sendRequest.SendRequestFragmentViewModel
import com.example.focustime.presentation.authorization.AuthorizationUserFragmentViewModel
import com.example.focustime.presentation.friends.FriendsFragmentViewModel
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorViewModel
import com.example.focustime.presentation.indicators.IndicatorsViewModel
import com.example.focustime.presentation.focus.FocusViewModel
import com.example.focustime.presentation.history.HistoryViewModel
import com.example.focustime.presentation.newFocus.NewFocusViewModel
import com.example.focustime.presentation.openTypeIndicator.OpenTypeIndicatorViewModel
import com.example.focustime.presentation.registration.RegistrationUserFragmentViewModel
import com.example.focustime.presentation.acceptRequest.AcceptRequestFragmentViewModel
import com.example.focustime.presentation.accountUser.AccountUserEditFragmentViewModel
import com.example.focustime.presentation.accountUser.AccountUserFragmentViewModel
import com.example.focustime.presentation.avatar.AvatarFragmentViewModel
import com.example.focustime.presentation.offlineSetting.OfflineSettingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationUserFragmentViewModel::class)
    abstract fun bindAuthorizationUserFragmentViewModel(viewModel: AuthorizationUserFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationUserFragmentViewModel::class)
    abstract fun bindRegistrationUserFragmentViewModel(viewModel: RegistrationUserFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendsFragmentViewModel::class)
    abstract fun bindFriendsFragmentViewModel(viewModel: FriendsFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AcceptRequestFragmentViewModel::class)
    abstract fun bindRequestFragmentViewModel(viewModel: AcceptRequestFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SendRequestFragmentViewModel::class)
    abstract fun bindAddFriendsViewModel(viewModel: SendRequestFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewTypeIndicatorViewModel::class)
    abstract fun bindNewTypeIndicatorViewModel(viewModel: NewTypeIndicatorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IndicatorsViewModel::class)
    abstract fun bindCreateTypeIndicatorViewModel(viewModel: IndicatorsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OpenTypeIndicatorViewModel::class)
    abstract fun bindOpenTypeIndicatorViewModel(viewModel: OpenTypeIndicatorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FocusViewModel::class)
    abstract fun bindFocusViewModel(viewModel: FocusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewFocusViewModel::class)
    abstract fun bindNewFocusViewModel(viewModel: NewFocusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindIndicatorsViewModel(viewModel: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountUserFragmentViewModel::class)
    abstract fun bindAccountUserFragmentViewModel(viewModel: AccountUserFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountUserEditFragmentViewModel::class)
    abstract fun bindAccountUserEditFragmentViewModel(viewModel: AccountUserEditFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AvatarFragmentViewModel::class)
    abstract fun bindAvatarFragmentViewModel(viewModel: AvatarFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfflineSettingViewModel::class)
    abstract fun bindOfflineSettingViewModel(viewModel: OfflineSettingViewModel): ViewModel
}