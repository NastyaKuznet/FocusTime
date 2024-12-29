package com.example.focustime.presentation.acceptRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.AcceptRequestUseCase
import com.example.focustime.domain.usecases.GetRequestUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.models.ResultUIFriends
import com.example.focustime.presentation.models.ResultUIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AcceptRequestFragmentViewModel @Inject constructor(
    private val getRequestUseCase: GetRequestUseCase,
    private val acceptRequestUseCase: AcceptRequestUseCase
) : ViewModel() {

    private val _uiStateRequest = MutableLiveData<ResultUIFriends>()
    val uiStateRequest: LiveData<ResultUIFriends>
        get() = _uiStateRequest

    private val _uiState = MutableLiveData<UIState<Unit>>()
    val uiState: LiveData<UIState<Unit>>
        get() = _uiState

    fun acceptRequest(userId1: Int, userId2: Int){
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            val result = acceptRequestUseCase(userId1, userId2)
            _uiState.value = result.toUIState()
        }
    }

    fun loadFriends(userId: Int){
        _uiStateRequest.value = ResultUIFriends(
            emptyList(),
            ResultUIState.Loading
        )
        viewModelScope.launch {
            val result = getRequestUseCase.invoke(userId)
            _uiStateRequest.value = ResultUIFriends(result.friends,
                when (result.success) {
                    true -> ResultUIState.Success
                    false -> ResultUIState.Error
                })
        }
    }
}