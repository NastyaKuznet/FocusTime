package com.example.focustime.presentation.focus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.domain.usecases.GetTypesIndicatorsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FocusViewModel @Inject constructor(
    private val getTypesIndicatorsUseCase: GetTypesIndicatorsUseCase,
): ViewModel() {

    private val _listTypesIndicators = MutableLiveData<List<String>>()
    val listTypeIndicators: LiveData<List<String>>
        get() = _listTypesIndicators

    private val _counter = MutableLiveData<Long>(0)
    val counter: LiveData<Long>
        get() = _counter

    private var typeIndicators = listOf<TypeIndicator>()

    fun getTypesIndicators(userId: Int){
        viewModelScope.launch {
            val result = getTypesIndicatorsUseCase(userId)
            typeIndicators = result
            val forSpinner = mutableListOf<String>()
            for(el in result){
                forSpinner.add(el.name)
            }
            _listTypesIndicators.value = forSpinner
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