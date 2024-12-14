package com.example.focustime.presentation.focus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.State
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.domain.usecases.GetTypesIndicatorsUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class FocusViewModel @Inject constructor(
    private val getTypesIndicatorsUseCase: GetTypesIndicatorsUseCase,
): ViewModel() {

    private val _listTypesIndicators = MutableLiveData<UIState<List<String>>>()
    val listTypeIndicators: LiveData<UIState<List<String>>>
        get() = _listTypesIndicators

    private val _counter = MutableLiveData<Long>(0)
    val counter: LiveData<Long>
        get() = _counter

    private var typeIndicators = listOf<TypeIndicator>()

    fun getTypesIndicators(userId: Int){
        viewModelScope.launch {
            _listTypesIndicators.value = UIState.Loading
            val result = getTypesIndicatorsUseCase(userId)
            if(result.state == State.FAIL){
                _listTypesIndicators.value = UIState.Fail(result.message)
                return@launch
            }
            typeIndicators = result.content
            val forSpinner = mutableListOf<String>()
            for(el in typeIndicators){
                forSpinner.add(el.name)
            }
            _listTypesIndicators.value = UIState.Success(forSpinner, result.message)
        }
    }

    fun getIdTypeIndByName(nameType: String): Int{
        if(typeIndicators.isNotEmpty()){
            for(el in typeIndicators){
                if(el.name == nameType){
                    return el.id
                }
            }
        }
        return 0
    }

    fun increment() {
        _counter.value = counter.value?.plus(1)
    }

    fun decrement() {
        _counter.value = counter.value?.minus(1)?.coerceAtLeast(0)
    }
}