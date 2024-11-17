package com.example.focustime.presentation.authorization

import androidx.lifecycle.ViewModel
import com.example.focustime.domain.usecases.AuthorizationUserUseCase
import com.example.focustime.presentation.registration.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope

class AuthorizationUserFragmentViewModel @Inject constructor(
    private val authorizationUserUseCase: AuthorizationUserUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Initial)
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun authorization(nickname: String, password: String){
        _uiState.value = RegistrationUiState.Loading
        viewModelScope.launch {
            val result = authorizationUserUseCase.invoke(nickname, password)
            _uiState.value = when (result) {
                true -> RegistrationUiState.Success
                false -> RegistrationUiState.Error
            }
        }

    }

}