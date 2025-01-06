package com.example.focustime.presentation.registration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.User
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import com.example.focustime.domain.usecases.UserValidation
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationUserFragmentViewModel @Inject constructor(
    private val registrationUserUseCase: RegistrationUserUseCase,
): ViewModel() {

    private val _stateRegistration = MutableLiveData<UIState<User>>()
    val stateRegistration: LiveData<UIState<User>>
        get() = _stateRegistration

    private val _stateCheckUserId = MutableLiveData<UIState<Int>>()
    val stateCheckUserId: LiveData<UIState<Int>>
        get() = _stateCheckUserId

    private val _stateCheckOfflineMode = MutableLiveData<UIState<Boolean>>()
    val stateCheckOfflineMode: LiveData<UIState<Boolean>>
        get() = _stateCheckOfflineMode


    fun registration(nickname: String, password: String){
        if(!UserValidation().validationRegistrationOrAuthorization(nickname, password)){
            _stateRegistration.value = UIState.Fail("Соблюдайте правила!")
            return
        }
        _stateRegistration.value = UIState.Loading
        viewModelScope.launch {
            val result = registrationUserUseCase.invoke(nickname, password)
            _stateRegistration.value = result.toUIState()
        }
    }

    fun checkLocaleUserId(context: Context){
        viewModelScope.launch {
            _stateCheckUserId.value = UIState.Loading
            val userId = run {
                val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                sharedPreferences.getInt("userId", 0)
            }
            _stateCheckUserId.value = when(userId){
                0 -> UIState.Fail("Нет сохраненого ид")
                else -> UIState.Success(userId, "Ид взято")
            }
        }
    }

    fun checkOfflineMode(context: Context){
        viewModelScope.launch {
            _stateCheckUserId.value = UIState.Loading
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val offlineMode = sharedPreferences.getBoolean("offlineMode", false)

            _stateCheckOfflineMode.value = when(offlineMode){
                false -> UIState.Fail("Ранее не было офлайн режима")
                true -> UIState.Success(offlineMode, "")
            }
        }
    }

}