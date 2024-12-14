package com.example.focustime.presentation.openTypeIndicator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustime.domain.usecases.DeleteTypeIndicatorUseCase
import com.example.focustime.domain.usecases.GetImagesUseCase
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

class OpenTypeIndicatorViewModel @Inject constructor(
    private val getIdImagesUseCase: GetImagesUseCase,
    private val deleteTypeIndicatorUseCase: DeleteTypeIndicatorUseCase,
): ViewModel() {

    private val _images = MutableLiveData<List<InputStream>>()
    val images: LiveData<List<InputStream>>
        get() = _images

    private val _stateDel = MutableLiveData<DeleteState>()
    val stateDel: LiveData<DeleteState>
        get() = _stateDel


    fun getImages(idType: Int){
        viewModelScope.launch {

            val result = getIdImagesUseCase(idType)
            _images.value = result
        }
    }

    fun deleteType(idType: Int){
        viewModelScope.launch {
            val result = deleteTypeIndicatorUseCase(idType)
            _stateDel.value = when(result){
                true -> DeleteState.SUCCESS
                false -> DeleteState.FAIL
            }

        }
    }
}