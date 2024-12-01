package com.example.focustime.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focustime.presentation.authorization.AuthorizationUserFragmentViewModel
import com.example.focustime.presentation.friends.FriendsFragmentViewModel
import com.example.focustime.presentation.registration.RegistrationUserFragmentViewModel
import com.example.focustime.presentation.request.RequestFragmentViewModel
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
    @ViewModelKey(RequestFragmentViewModel::class)
    abstract fun bindRequestFragmentViewModel(viewModel: RequestFragmentViewModel): ViewModel
}