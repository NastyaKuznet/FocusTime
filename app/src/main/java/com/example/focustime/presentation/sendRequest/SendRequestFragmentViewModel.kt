package com.example.focustime.presentation.sendRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.domain.usecases.SendFriendRequestUseCase
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class SendRequestFragmentViewModel  @Inject constructor(
    private val sendFriendRequestUseCase: SendFriendRequestUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<ResultUI>()
    val uiState: LiveData<ResultUI>
        get() = _uiState

    fun sendFriendRequest(user1Id: Int, user2Nickname: String) {
        if (user1Id == 0 || user2Nickname == "") {
            _uiState.value = ResultUI(
                ResultUIState.Error
            )
            return
        }

        _uiState.value = ResultUI(
            ResultUIState.Loading
        )
        viewModelScope.launch {
            val result = sendFriendRequestUseCase.invoke(user1Id, user2Nickname)
            _uiState.value = ResultUI(
                when (result.success) {
                    true -> ResultUIState.Success
                    false -> ResultUIState.Error
                }
            )
        }
    }
}