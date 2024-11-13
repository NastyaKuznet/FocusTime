package com.example.focustime.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationUserFragmentViewModel @Inject constructor(
    private val registrationUserUseCase: RegistrationUserUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Initial)
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun registration(nickname: String, password: String){
        _uiState.value = RegistrationUiState.Loading
        viewModelScope.launch {
            val result = registrationUserUseCase.invoke(nickname, password)
            _uiState.value = when (result) {
                true -> RegistrationUiState.Success
                false -> RegistrationUiState.Error
            }
        }

    }

}