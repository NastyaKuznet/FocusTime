package com.example.focustime.presentation.openTypeIndicator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.DeleteTypeIndicatorUseCase
import com.example.focustime.domain.usecases.GetImagesUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

class OpenTypeIndicatorViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val deleteTypeIndicatorUseCase: DeleteTypeIndicatorUseCase,
): ViewModel() {

    private val _images = MutableLiveData<UIState<List<InputStream>>>()
    val images: LiveData<UIState<List<InputStream>>>
        get() = _images

    private val _stateDel = MutableLiveData<UIState<Unit>>()
    val stateDel: LiveData<UIState<Unit>>
        get() = _stateDel


    fun getImages(idType: Int){
        viewModelScope.launch {
            _images.value = UIState.Loading
            val result = getImagesUseCase(idType)
            _images.value = result.toUIState()
        }
    }

    fun deleteType(idType: Int){
        viewModelScope.launch {
            _stateDel.value = UIState.Loading
            val result = deleteTypeIndicatorUseCase(idType)
            _stateDel.value = result.toUIState()
        }
    }
}