package com.example.focustime.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.User
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import com.example.focustime.domain.usecases.UserValidation
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.models.ResultUIUser
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationUserFragmentViewModel @Inject constructor(
    private val registrationUserUseCase: RegistrationUserUseCase,
): ViewModel() {

    private val _uiState = MutableLiveData<UIState<User>>()
    val uiState: LiveData<UIState<User>>
        get() = _uiState

    fun registration(nickname: String, password: String){
        if(!UserValidation().validationRegistrationOrAuthorization(nickname, password)){
            _uiState.value = UIState.Fail("Соблюдайте правила!")
            return
        }
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            val result = registrationUserUseCase.invoke(nickname, password)
            _uiState.value = result.toUIState()
        }
    }

}