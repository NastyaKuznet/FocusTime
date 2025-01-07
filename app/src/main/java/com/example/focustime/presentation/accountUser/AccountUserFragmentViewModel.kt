package com.example.focustime.presentation.accountUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.database.model.UserInfoEntity
import com.example.focustime.data.models.Indicator
import com.example.focustime.data.models.UserInfo
import com.example.focustime.domain.usecases.DeleteUserIdLocaleUseCase
import com.example.focustime.domain.usecases.GetAllIndicatorsUseCase
import com.example.focustime.domain.usecases.getUserInfoUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AccountUserFragmentViewModel @Inject constructor(
    private val deleteUserIdLocaleUseCase: DeleteUserIdLocaleUseCase,
    private val sendFriendRequestUseCase: getUserInfoUseCase,
    private val getAllIndicatorsUseCase: GetAllIndicatorsUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState<UserInfo>>()
    val uiState: LiveData<UIState<UserInfo>>
        get() = _uiState

    private val _stateCheckUserId = MutableLiveData<UIState<Unit>>()
    val stateCheckUserId : LiveData<UIState<Unit>>
        get() = _stateCheckUserId

    private val _currentIndicators = MutableLiveData<UIState<List<Indicator>>>()
    val currentIndicators: LiveData<UIState<List<Indicator>>>
        get() = _currentIndicators

    private var indicators: UIState<List<Indicator>> = UIState.Loading
    private var currentDate = "2024-01-01"

    private fun getDateNow(){
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        currentDate = formatter.format(Date())
    }

    fun getIndicators(userId: Int){
        getDateNow()
        viewModelScope.launch {
            _currentIndicators.value = UIState.Loading
            val result = getAllIndicatorsUseCase(userId)
            indicators = result.toUIState()
            when(indicators) {
                is UIState.Success -> {

                    _currentIndicators.value = indicators
                }
                is UIState.Fail -> _currentIndicators.value = indicators
                else -> {}
            }
        }
    }


    fun getUserInfo(userId: Int) {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            val result = sendFriendRequestUseCase.invoke(userId)
            _uiState.value = result.toUIState()
        }
    }

    fun deleteUserIdLocale(userId: Int){
        viewModelScope.launch {
            _stateCheckUserId.value = UIState.Loading
            val result = withContext(Dispatchers.IO) {
                deleteUserIdLocaleUseCase(userId)
            }
            _stateCheckUserId.value = result.toUIState()
        }
    }
}