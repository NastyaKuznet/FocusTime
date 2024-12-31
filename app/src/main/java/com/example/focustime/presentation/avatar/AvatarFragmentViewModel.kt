package com.example.focustime.presentation.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.UpdateAvatarUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AvatarFragmentViewModel @Inject constructor(
    private val updateAvatarUseCase: UpdateAvatarUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState<Unit>>()
    val uiState: LiveData<UIState<Unit>>
        get() = _uiState

    fun updateAvatar(userId: Int, newAvatarId: Int){
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            val result = updateAvatarUseCase(userId, newAvatarId)
            _uiState.value = result.toUIState()
        }
    }
}
