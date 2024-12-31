package com.example.focustime.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.State
import com.example.focustime.data.database.model.UserInfoEntity
import com.example.focustime.data.models.User
import com.example.focustime.domain.usecases.GetUserIdLocaleUseCase
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import com.example.focustime.domain.usecases.SaveUserIdInLocaleUseCase
import com.example.focustime.domain.usecases.UserValidation
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegistrationUserFragmentViewModel @Inject constructor(
    private val registrationUserUseCase: RegistrationUserUseCase,
    private val getUserIdLocaleUseCase: GetUserIdLocaleUseCase,
    private val saveUserIdInLocaleUseCase: SaveUserIdInLocaleUseCase,
): ViewModel() {

    private val _stateRegistration = MutableLiveData<UIState<User>>()
    val stateRegistration: LiveData<UIState<User>>
        get() = _stateRegistration

    private val _stateCheckUserId = MutableLiveData<UIState<UserInfoEntity>>()
    val stateCheckUserId: LiveData<UIState<UserInfoEntity>>
        get() = _stateCheckUserId


    fun registration(nickname: String, password: String){
        if(!UserValidation().validationRegistrationOrAuthorization(nickname, password)){
            _stateRegistration.value = UIState.Fail("Соблюдайте правила!")
            return
        }
        _stateRegistration.value = UIState.Loading
        viewModelScope.launch {
            val result = registrationUserUseCase.invoke(nickname, password)
            _stateRegistration.value = result.toUIState()
            if(result.state == State.SUCCESS){
                saveUserIdInLocaleUseCase(result.content.id)
            }
        }
    }

    fun checkLocaleUserId(){
        viewModelScope.launch {
            _stateCheckUserId.value = UIState.Loading
            val result = withContext(Dispatchers.IO) {
                getUserIdLocaleUseCase()
            }
            _stateCheckUserId.value = result.toUIState()
        }
    }

}