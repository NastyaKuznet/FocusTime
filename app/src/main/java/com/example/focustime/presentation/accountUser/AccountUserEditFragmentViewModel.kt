package com.example.focustime.presentation.accountUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.UpdateUserInfoUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountUserEditFragmentViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState<Unit>>()
    val uiState: LiveData<UIState<Unit>>
        get() = _uiState

    fun updateUserInfo(userId: Int, status: String){
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            val result = updateUserInfoUseCase.invoke(status, userId)
            _uiState.value = result.toUIState()
        }
    }
}