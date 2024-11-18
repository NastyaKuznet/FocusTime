package com.example.focustime.presentation.authorization

import androidx.lifecycle.ViewModel
import com.example.focustime.domain.usecases.AuthorizationUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.User
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.domain.usecases.UserValidation
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.models.ResultUIUser

class AuthorizationUserFragmentViewModel @Inject constructor(
    private val authorizationUserUseCase: AuthorizationUserUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(
        ResultUIUser(
            User(0,"","",""),
            ResultUIState.Initial)
    )
    val uiState: StateFlow<ResultUIUser> = _uiState.asStateFlow()

    fun authorization(nickname: String, password: String){
        if(!UserValidation().validationRegistrationOrAuthorization(nickname, password)){
            _uiState.value = ResultUIUser(
                User(0,"","",""),
                ResultUIState.Error)
            return
        }

        _uiState.value = ResultUIUser(
            User(0,"","",""),
            ResultUIState.Loading)
        viewModelScope.launch {
            val result = authorizationUserUseCase.invoke(nickname, password)
            _uiState.value = ResultUIUser(result.user,
                when(result.stateResult){
                    true -> ResultUIState.Success
                    false -> ResultUIState.Error
                })
        }
    }

}