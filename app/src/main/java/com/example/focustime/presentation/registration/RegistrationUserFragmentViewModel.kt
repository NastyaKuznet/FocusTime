package com.example.focustime.presentation.registration

import androidx.lifecycle.ViewModel
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import javax.inject.Inject

class RegistrationUserFragmentViewModel @Inject constructor(
    private val registrationUserUseCase: RegistrationUserUseCase,
): ViewModel() {

}