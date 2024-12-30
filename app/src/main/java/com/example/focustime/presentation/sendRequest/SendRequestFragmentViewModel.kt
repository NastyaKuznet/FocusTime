package com.example.focustime.presentation.sendRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.domain.usecases.SendFriendRequestUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendRequestFragmentViewModel  @Inject constructor(
    private val sendFriendRequestUseCase: SendFriendRequestUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState<Unit>>()
    val uiState: LiveData<UIState<Unit>>
        get() = _uiState

    fun sendFriendRequest(user1Id: Int, user2Nickname: String){
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            val result = sendFriendRequestUseCase.invoke(user1Id, user2Nickname)
            _uiState.value = result.toUIState()
        }
    }
}