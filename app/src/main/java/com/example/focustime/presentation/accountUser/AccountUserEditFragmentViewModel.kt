package com.example.focustime.presentation.accountUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.SendFriendRequestUseCase
import com.example.focustime.domain.usecases.UpdateUserInfoUseCase
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountUserEditFragmentViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<ResultUI>()
    val uiState: LiveData<ResultUI>
        get() = _uiState

    fun updateUserInfo(userId: Int, status: String) {
        if (userId == 0 || status == "") {
            _uiState.value = ResultUI(
                ResultUIState.Error
            )
            return
        }

        _uiState.value = ResultUI(
            ResultUIState.Loading
        )
        viewModelScope.launch {
            val result = updateUserInfoUseCase.invoke(status, userId)
            _uiState.value = ResultUI(
                when (result.success) {
                    true -> ResultUIState.Success
                    false -> ResultUIState.Error
                }
            )
        }
    }
}