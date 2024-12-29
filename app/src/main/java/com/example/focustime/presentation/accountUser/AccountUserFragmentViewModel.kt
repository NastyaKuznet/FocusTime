package com.example.focustime.presentation.accountUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.UserInfo
import com.example.focustime.domain.usecases.SendFriendRequestUseCase
import com.example.focustime.domain.usecases.UpdateAvatarUseCase
import com.example.focustime.domain.usecases.getUserInfoUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.presentation.models.ResultUIFriends
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.models.ResultUIUserInfo
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountUserFragmentViewModel @Inject constructor(
    private val sendFriendRequestUseCase: getUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<ResultUIUserInfo>()
    val uiState: LiveData<ResultUIUserInfo>
        get() = _uiState

    fun getUserInfo(user1Id: Int) {
        if (user1Id == 0) {
            _uiState.value = ResultUIUserInfo(
                UserInfo("","",0,0),
                ResultUIState.Error
            )
            return
        }

        _uiState.value = ResultUIUserInfo(
            UserInfo("","",0,0),
            ResultUIState.Loading
        )
        viewModelScope.launch {
            val result = sendFriendRequestUseCase.invoke(user1Id)
            _uiState.value = ResultUIUserInfo(result,ResultUIState.Success)
        }
    }
}