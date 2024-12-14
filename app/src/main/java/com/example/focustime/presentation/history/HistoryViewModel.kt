package com.example.focustime.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.Indicator
import com.example.focustime.domain.usecases.GetAllIndicatorsUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val getAllIndicatorsUseCase: GetAllIndicatorsUseCase,
): ViewModel() {

    private val _currentIndicators = MutableLiveData<List<Indicator>>()
    val currentIndicators: LiveData<List<Indicator>>
        get() = _currentIndicators

    private var indicators = listOf<Indicator>()
    private var currentDate = "2024-01-01"

    private fun getDateNow(){
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        currentDate = formatter.format(Date())
    }

    fun getIndicators(userId: Int){
        getDateNow()
        viewModelScope.launch {
            val result = getAllIndicatorsUseCase(userId)
            indicators = result
            getIndicatorsAllTime()
        }
    }

    fun getIndicatorsAllTime(){
        _currentIndicators.value = indicators
    }

    fun getIndicatorsDay(){
        val lengthCD = currentDate.length
        val day = currentDate.substring(lengthCD - 2, lengthCD)
        val result = mutableListOf<Indicator>()
        for(el in indicators){
            val lengthID = el.day.length
            if(el.day.substring(lengthID - 2, lengthID) == day){
                result.add(el)
            }
        }
        _currentIndicators.value = result
    }

    fun getIndicatorsMonth(){
        val lengthCD = currentDate.length
        val month = currentDate.substring(lengthCD - 5, lengthCD - 2)
        val result = mutableListOf<Indicator>()
        for(el in indicators){
            val lengthID = el.day.length
            if(el.day.substring(lengthID - 5, lengthID - 2) == month){
                result.add(el)
            }
        }
        _currentIndicators.value = result
    }

    fun getIndicatorsYear(){
        val year = currentDate.substring(0, 4)
        val result = mutableListOf<Indicator>()
        for(el in indicators){
            if(el.day.substring(0, 4) == year){
                result.add(el)
            }
        }
        _currentIndicators.value = result
    }


}