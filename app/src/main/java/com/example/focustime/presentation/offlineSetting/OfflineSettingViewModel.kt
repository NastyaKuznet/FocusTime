package com.example.focustime.presentation.offlineSetting

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.localDatabase.indicator.DeleteAllIndicatorsLocalUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineSettingViewModel @Inject constructor(
    private val deleteAllIndicatorsLocalUseCase: DeleteAllIndicatorsLocalUseCase,
): ViewModel() {

    private val _stateDelete = MutableLiveData<UIState<Unit>>()
    val stateDelete: LiveData<UIState<Unit>>
        get() = _stateDelete

    fun deleteIndicators(){
        viewModelScope.launch {
            _stateDelete.value = UIState.Loading
            val result = deleteAllIndicatorsLocalUseCase()
            _stateDelete.value = result.toUIState()
        }
    }

    fun exitOfflineMode(context: Context){
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("offlineMode", false)
        editor.apply()
    }
}