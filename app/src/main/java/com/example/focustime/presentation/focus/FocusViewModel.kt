package com.example.focustime.presentation.focus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.State
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.domain.usecases.GetTypesIndicatorsUseCase
import com.example.focustime.presentation.UIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class FocusViewModel @Inject constructor(
    private val getTypesIndicatorsUseCase: GetTypesIndicatorsUseCase,
): ViewModel() {

    private val _listTypesIndicators = MutableLiveData<UIState<List<String>>>()
    val listTypeIndicators: LiveData<UIState<List<String>>>
        get() = _listTypesIndicators

    private val _second = MutableLiveData<Long>(0)
    val second: LiveData<Long>
        get() = _second

    private val _minute = MutableLiveData<Long>(0)
    val minute: LiveData<Long>
        get() = _minute

    private val _hour = MutableLiveData<Long>(0)
    val hour: LiveData<Long>
        get() = _hour

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

    fun incrementSecond() {
        _second.value = (_second.value ?: 0).inc().rem(60)
    }

    fun decrementSecond() {
        _second.value = (_second.value ?: 0).minus(1).let {
            if(it < 0) 59 else it
        }
    }

    fun incrementMinute() {
        _minute.value = (_minute.value ?: 0).inc().rem(60)
    }

    fun decrementMinute() {
        _minute.value = (_minute.value ?: 0).minus(1).let {
            if(it < 0) 59 else it
        }
    }

    fun incrementHour() {
        _hour.value = _hour.value?.plus(1)
    }

    fun decrementHour() {
        _hour.value = (_hour.value ?: 0).minus(1).let {
            if(it < 0) 23 else it
        }
    }
}