package com.example.focustime.presentation.createTypeIndicator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.domain.usecases.GetTypesIndicatorsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTypeIndicatorViewModel @Inject constructor(
    private val getTypesIndicatorsUseCase: GetTypesIndicatorsUseCase,
): ViewModel() {

    private val _listTypesIndicators = MutableLiveData<List<TypeIndicator>>()
    val listTypeIndicators: LiveData<List<TypeIndicator>>
        get() = _listTypesIndicators

    fun getTypesIndicators(userId: Int){
        viewModelScope.launch {
            val result = getTypesIndicatorsUseCase(userId)
            _listTypesIndicators.value = result
        }
    }

}