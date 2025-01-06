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
import com.example.focustime.domain.usecases.localDatabase.image.GetImagesByIdTypeLocalUseCase
import com.example.focustime.domain.usecases.localDatabase.indicator.AddIndicatorLocalUseCase
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
    private val getImagesByIdTypeLocalUseCase: GetImagesByIdTypeLocalUseCase,
    private val addIndicatorLocalUseCase: AddIndicatorLocalUseCase,
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

    private val _stateLoadingImages = MutableLiveData<UIState<List<InputStream>>>()
    val stateLoadingImages: LiveData<UIState<List<InputStream>>>
        get() = _stateLoadingImages

    private var job: Job? = null
    private var images = MutableLiveData<MutableList<InputStream>>()


    fun startTimer(offlineMode: Boolean, endTime: Int, idType: Int, userId: Int) {
        var endT = 0
        if(endTime < 5) {
            endT = 5
        } else{
            endT = endTime
        }
        val step = endT / 5 //константа стадий
        var count = 0

        job = viewModelScope.launch {
            _stateLoadingImages.value = UIState.Loading
            if(offlineMode){
                val resultLocal = getImagesByIdTypeLocalUseCase(idType)
                _stateLoadingImages.value = resultLocal.toUIState()
                if (resultLocal.state == State.FAIL) {
                    Log.d("startTimer", "image not get")
                    return@launch
                }
                images.value = resultLocal.content.toMutableList()
            } else {
                val result = getImagesUseCase(idType)
                _stateLoadingImages.value = result.toUIState()
                if (result.state == State.FAIL) {
                    Log.d("startTimer", "image not get")
                    return@launch
                }
                images.value = result.content.toMutableList()
            }

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
            if(offlineMode){
                val savedStateLocale = addIndicatorLocalUseCase(endTime, idType, formattedDateTime)
                _stateTime.value = savedStateLocale.toUIState()
            } else {
                val savedState = addIndicatorUseCase(userId, endTime, idType, formattedDateTime)
                _stateTime.value = savedState.toUIState()
            }
        }
    }

    fun pauseTimer() {
        job?.cancel()
        job = null
    }

}