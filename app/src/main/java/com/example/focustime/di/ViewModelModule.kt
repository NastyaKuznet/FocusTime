package com.example.focustime.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focustime.presentation.authorization.AuthorizationUserFragmentViewModel
import com.example.focustime.presentation.createNewTypeIndicator.NewTypeIndicatorViewModel
import com.example.focustime.presentation.createTypeIndicator.CreateTypeIndicatorViewModel
import com.example.focustime.presentation.focus.FocusViewModel
import com.example.focustime.presentation.newFocus.NewFocusViewModel
import com.example.focustime.presentation.openTypeIndicator.OpenTypeIndicatorViewModel
import com.example.focustime.presentation.registration.RegistrationUserFragmentViewModel
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
    @ViewModelKey(NewTypeIndicatorViewModel::class)
    abstract fun bindNewTypeIndicatorViewModel(viewModel: NewTypeIndicatorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateTypeIndicatorViewModel::class)
    abstract fun bindCreateTypeIndicatorViewModel(viewModel: CreateTypeIndicatorViewModel): ViewModel

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
}