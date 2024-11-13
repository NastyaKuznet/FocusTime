package com.example.focustime.presentation.authorization

import androidx.lifecycle.ViewModel
import com.example.focustime.domain.usecases.AuthorizationUserUseCase
import javax.inject.Inject

class AuthorizationUserFragmentViewModel @Inject constructor(
    private val authorizationUserUseCase: AuthorizationUserUseCase,
): ViewModel() {

}