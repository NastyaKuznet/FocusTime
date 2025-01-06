package com.example.focustime.presentation.accountUser

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.UserInfo
import com.example.focustime.domain.usecases.getUserInfoUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountUserFragmentViewModel @Inject constructor(
    private val sendFriendRequestUseCase: getUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState<UserInfo>>()
    val uiState: LiveData<UIState<UserInfo>>
        get() = _uiState

    private val _stateCheckUserId = MutableLiveData<UIState<Unit>>()
    val stateCheckUserId : LiveData<UIState<Unit>>
        get() = _stateCheckUserId


    fun getUserInfo(userId: Int) {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            val result = sendFriendRequestUseCase.invoke(userId)
            _uiState.value = result.toUIState()
        }
    }

    fun deleteUserIdLocale(context: Context){
        viewModelScope.launch {
            _stateCheckUserId.value = UIState.Loading
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("userId", 0)
            editor.putBoolean("offlineMode", false)
            editor.apply()
            _stateCheckUserId.value = UIState.Success(Unit, "")
        }
    }
}