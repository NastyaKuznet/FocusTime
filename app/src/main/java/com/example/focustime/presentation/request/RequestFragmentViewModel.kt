package com.example.focustime.presentation.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.GetFriendsUseCase
import com.example.focustime.domain.usecases.GetRequestUseCase
import com.example.focustime.presentation.models.ResultUIFriends
import com.example.focustime.presentation.models.ResultUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestFragmentViewModel @Inject constructor(
    private val getRequestUseCase: GetRequestUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<ResultUIFriends>()
    val uiState: LiveData<ResultUIFriends>
        get() = _uiState

    fun loadFriends(userId: Int) {
        if (userId == 0) {
            _uiState.value = ResultUIFriends(
                emptyList(),
                ResultUIState.Error
            )
            return
        }

        _uiState.value = ResultUIFriends(
            emptyList(),
            ResultUIState.Loading
        )
        viewModelScope.launch {
            val result = getRequestUseCase.invoke(userId)
            _uiState.value = ResultUIFriends(result.friends,
                when (result.success) {
                    true -> ResultUIState.Success
                    false -> ResultUIState.Error
                })
        }
    }
}