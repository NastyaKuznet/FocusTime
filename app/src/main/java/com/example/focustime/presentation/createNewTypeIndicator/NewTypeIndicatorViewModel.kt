package com.example.focustime.presentation.createNewTypeIndicator

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import com.example.focustime.domain.usecases.*
import com.example.focustime.domain.usecases.localDatabase.typeIndicator.AddTypeIndicatorLocalUseCase
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.toUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class NewTypeIndicatorViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val addTypeIndicatorUseCase: AddTypeIndicatorUseCase,
    private val addTypeIndicatorLocalUseCase: AddTypeIndicatorLocalUseCase,
): ViewModel() {

    private val _selectedImages = MutableLiveData<List<Uri?>>()

    private val _resultSave = MutableLiveData<UIState<Unit>>()
    val resultSave: LiveData<UIState<Unit>>
        get() = _resultSave

    fun setImages(list: List<Uri>){
        _selectedImages.value = list
    }

    fun save(offlineMode: Boolean, context: Context, typeName: String, userId: Int){
        val listId = mutableListOf<Int>()
        if(typeName.isEmpty() || _selectedImages.value == null){
            _resultSave.value = UIState.Fail("Все поля должны быть заполнены")
            return
        }
        viewModelScope.launch {
            _resultSave.value = UIState.Loading
            if(offlineMode){
                val resultLocal = addTypeIndicatorLocalUseCase(context, typeName, _selectedImages.value!!)
                _resultSave.value = resultLocal.toUIState()
            } else {
                for (el in _selectedImages.value!!) {
                    val tempFile = createTempFileFromUri(el!!, context)
                    tempFile?.let { file ->
                        val result = uploadImageUseCase(file)
                        listId.add(result.content)
                    }
                }
                val result = addTypeIndicatorUseCase(userId, typeName, listId)
                _resultSave.value = result.toUIState()
            }
        }
    }


    //с нулом разобраться
    private suspend fun createTempFileFromUri(uri: Uri, context: Context): File? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri) ?: return@withContext null
                val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
                val outputStream = FileOutputStream(tempFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                tempFile
            } catch (e: Exception) {
                null
            }
        }
    }
}