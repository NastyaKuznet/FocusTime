package com.example.focustime.presentation.openTypeIndicator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.DeleteTypeIndicatorUseCase
import com.example.focustime.domain.usecases.GetImagesUseCase
import com.example.focustime.domain.usecases.localDatabase.image.GetImagesByIdTypeLocalUseCase
import com.example.focustime.domain.usecases.localDatabase.typeIndicator.DeleteTypeIndicatorLocalUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

class OpenTypeIndicatorViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val deleteTypeIndicatorUseCase: DeleteTypeIndicatorUseCase,
    private val getImagesByIdTypeLocalUseCase: GetImagesByIdTypeLocalUseCase,
    private val deleteTypeIndicatorLocalUseCase: DeleteTypeIndicatorLocalUseCase,
): ViewModel() {

    private val _images = MutableLiveData<UIState<List<InputStream>>>()
    val images: LiveData<UIState<List<InputStream>>>
        get() = _images

    private val _stateDel = MutableLiveData<UIState<Unit>>()
    val stateDel: LiveData<UIState<Unit>>
        get() = _stateDel


    fun getImages(offlineMode: Boolean, idType: Int){
        viewModelScope.launch {
            _images.value = UIState.Loading
            if(offlineMode){
                val resultLocal = getImagesByIdTypeLocalUseCase(idType)
                _images.value = resultLocal.toUIState()
            } else {
                val result = getImagesUseCase(idType)
                _images.value = result.toUIState()
            }
        }
    }

    fun deleteType(offlineMode: Boolean, idType: Int){
        viewModelScope.launch {
            _stateDel.value = UIState.Loading
            if(offlineMode){
                val resultLocal = deleteTypeIndicatorLocalUseCase(idType)
                _stateDel.value = resultLocal.toUIState()
            } else {
                val result = deleteTypeIndicatorUseCase(idType)
                _stateDel.value = result.toUIState()
            }
        }
    }
}