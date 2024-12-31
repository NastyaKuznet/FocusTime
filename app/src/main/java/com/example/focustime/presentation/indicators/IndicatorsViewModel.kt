package com.example.focustime.presentation.indicators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.domain.usecases.GetTypesIndicatorsUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class IndicatorsViewModel @Inject constructor(
    private val getTypesIndicatorsUseCase: GetTypesIndicatorsUseCase,
): ViewModel() {

    private val _listTypesIndicators = MutableLiveData<UIState<List<TypeIndicator>>>()
    val listTypeIndicators: LiveData<UIState<List<TypeIndicator>>>
        get() = _listTypesIndicators

    fun getTypesIndicators(userId: Int){
        _listTypesIndicators.value = UIState.Loading
        viewModelScope.launch {
            val result = getTypesIndicatorsUseCase(userId)
            _listTypesIndicators.value = result.toUIState()
        }
    }

}