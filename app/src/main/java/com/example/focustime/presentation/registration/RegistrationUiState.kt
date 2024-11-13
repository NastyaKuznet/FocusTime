package com.example.focustime.presentation.registration

sealed class RegistrationUiState {
    data object Initial : RegistrationUiState()
    data object Loading : RegistrationUiState()
    data object Success : RegistrationUiState()
    data object Error : RegistrationUiState()
}