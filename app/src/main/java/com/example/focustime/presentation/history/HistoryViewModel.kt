package com.example.focustime.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.models.Indicator
import com.example.focustime.domain.usecases.GetAllIndicatorsUseCase
import com.example.focustime.domain.usecases.localDatabase.indicator.GetAllIndicatorsLocalUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val getAllIndicatorsUseCase: GetAllIndicatorsUseCase,
    private val getAllIndicatorsLocalUseCase: GetAllIndicatorsLocalUseCase,
): ViewModel() {

    private val _currentIndicators = MutableLiveData<UIState<List<Indicator>>>()
    val currentIndicators: LiveData<UIState<List<Indicator>>>
        get() = _currentIndicators

    private var indicators: UIState<List<Indicator>> = UIState.Loading
    private var currentDate = "2024-01-01"

    private fun getDateNow(){
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        currentDate = formatter.format(Date())
    }

    fun getIndicators(offlineMode: Boolean, userId: Int){
        getDateNow()
        viewModelScope.launch {
            _currentIndicators.value = UIState.Loading
            val result = if(offlineMode){
                getAllIndicatorsLocalUseCase()
            } else{
                getAllIndicatorsUseCase(userId)
            }
            indicators = result.toUIState()
            _currentIndicators.value = indicators
        }
    }

    fun filterIndicators(filterType: FilterType) {
        viewModelScope.launch {
            if (indicators is UIState.Success) {
                val filteredIndicators = when (filterType) {
                    FilterType.ALL -> (indicators as UIState.Success).value
                    FilterType.DAY -> filterByDay((indicators as UIState.Success).value)
                    FilterType.MONTH -> filterByMonth((indicators as UIState.Success).value)
                    FilterType.YEAR -> filterByYear((indicators as UIState.Success).value)
                }
                _currentIndicators.value = UIState.Success(filteredIndicators, "Готово")
            } else if (indicators is UIState.Fail) {
                _currentIndicators.value = indicators
            }
        }
    }

    private fun filterByDay(indicators: List<Indicator>): List<Indicator> {
        val day = currentDate.substring(currentDate.length - 2)
        return indicators.filter { it.day.substring(8,10) == day }
    }

    private fun filterByMonth(indicators: List<Indicator>): List<Indicator> {
        val month = currentDate.substring(currentDate.length - 5, currentDate.length - 3)
        return indicators.filter { it.day.substring(5,7) == month }
    }

    private fun filterByYear(indicators: List<Indicator>): List<Indicator> {
        val year = currentDate.substring(0, 4)
        return indicators.filter { it.day.startsWith(year) }
    }

    enum class FilterType {
        ALL,
        DAY,
        MONTH,
        YEAR
    }
}