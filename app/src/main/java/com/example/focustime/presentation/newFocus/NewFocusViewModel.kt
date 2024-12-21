package com.example.focustime.presentation.newFocus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.focustime.data.State
import com.example.focustime.domain.usecases.AddIndicatorUseCase
import com.example.focustime.domain.usecases.GetImagesUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewFocusViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val addIndicatorUseCase: AddIndicatorUseCase,
): ViewModel() {

    private val _time = MutableLiveData(0L)
    val time: LiveData<Long>
        get() = _time

    private val _stateTime = MutableLiveData<UIState<Unit>>()
    val stateTime: LiveData<UIState<Unit>>
        get() = _stateTime

    private val _selectedImage = MutableLiveData<InputStream>()
    val selectedImage: LiveData<InputStream>
        get() = _selectedImage

    private var job: Job? = null
    private var images = MutableLiveData<MutableList<InputStream>>()


    fun startTimer(endTime: Int, idType: Int, userId: Int) {
        val step = endTime / 5 //константа стадий
        var count = 0
        job = viewModelScope.launch {
            val result = getImagesUseCase(idType)
            if(result.state == State.FAIL){
                Log.d("startTimer", "image not get")
                return@launch
            }
            images.value = result.content.toMutableList()

            while (_time.value != endTime.toLong()) {
                delay(1000L)
                if(_time.value!! % step == 0L && count < images.value?.size ?: 0){
                    _selectedImage.value = images.value?.get(count)
                    count++
                }
                if(_selectedImage.value == null){
                    _selectedImage.value = images.value?.get(count)
                                    }
                _time.value = (_time.value ?: 0L) + 1L

            }
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDateTime = formatter.format(Date())
            val savedState = addIndicatorUseCase(userId, endTime, idType, formattedDateTime)
            _stateTime.value = savedState.toUIState()
        }
    }

    fun pauseTimer() {
        job?.cancel()
        job = null
    }

}