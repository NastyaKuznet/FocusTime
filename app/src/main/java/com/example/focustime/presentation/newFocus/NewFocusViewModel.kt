package com.example.focustime.presentation.newFocus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.AddIndicatorUseCase
import com.example.focustime.domain.usecases.GetImagesUseCase
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

    private val _stateTime = MutableLiveData(false)
    val stateTime: LiveData<Boolean>
        get() = _stateTime

    private val _selectedImage = MutableLiveData<InputStream>()
    val selectedImage: LiveData<InputStream>
        get() = _selectedImage

    private var job: Job? = null
    private var images = MutableLiveData<MutableList<InputStream>>()


    fun startTimer(endTime: Long, idType: Int, userId: Int) {

        val endT = endTime * 60
        val step = endT / 5 //константа стадий
        var count = 0
        job = viewModelScope.launch {
            val result = getImagesUseCase(idType)
            images.value = result.toMutableList()

            while (_time.value != endT) {
                delay(1000L)
                if(_time.value!! % step == 0L){
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
            addIndicatorUseCase(userId, endT.toInt(), idType, formattedDateTime)
            _stateTime.value = true
        }
    }

    fun pauseTimer() {
        job?.cancel()
        job = null
    }

}