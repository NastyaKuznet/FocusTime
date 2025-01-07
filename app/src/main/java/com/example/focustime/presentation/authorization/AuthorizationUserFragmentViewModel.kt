package com.example.focustime.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.focustime.domain.usecases.AuthorizationUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.User
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState

class AuthorizationUserFragmentViewModel @Inject constructor(
    private val authorizationUserUseCase: AuthorizationUserUseCase,
): ViewModel() {

    private val _uiState = MutableLiveData<UIState<User>>()
    val uiState: LiveData<UIState<User>>
        get() = _uiState

    fun authorization(nickname: String, password: String){
        /*if(!UserValidation().validationRegistrationOrAuthorization(nickname, password)){
            _uiState.value = ResultUIUser(
                User(0,"","",""),
                ResultUIState.Error)
            return
        }*/

        _uiState.value = UIState.Loading
        viewModelScope.launch {
            val result = authorizationUserUseCase.invoke(nickname, password)
            _uiState.value = result.toUIState()
        }
    }

}