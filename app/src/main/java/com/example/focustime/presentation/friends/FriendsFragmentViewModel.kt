package com.example.focustime.presentation.friends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.GetFriendsUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsFragmentViewModel @Inject constructor(
    private val getFriendsUseCase: GetFriendsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState<List<Friend>>>()
    val uiState: LiveData<UIState<List<Friend>>>
        get() = _uiState

    fun loadFriends(userId: Int) {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            val result = getFriendsUseCase.invoke(userId)
            _uiState.value = result.toUIState()
        }
    }
}