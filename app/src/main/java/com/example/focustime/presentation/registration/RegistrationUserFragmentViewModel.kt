package com.example.focustime.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.User
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import com.example.focustime.domain.usecases.UserValidation
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.models.ResultUIUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationUserFragmentViewModel @Inject constructor(
    private val registrationUserUseCase: RegistrationUserUseCase,
): ViewModel() {

    private val _uiState = MutableLiveData<ResultUIUser>()
    val uiState: LiveData<ResultUIUser>
        get() = _uiState

    fun registration(nickname: String, password: String){
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
            val result = registrationUserUseCase.invoke(nickname, password)
            _uiState.value = ResultUIUser(result.user,
                when(result.stateResult){
                    true -> ResultUIState.Success
                    false -> ResultUIState.AlreadyExists
                })
        }
    }

}