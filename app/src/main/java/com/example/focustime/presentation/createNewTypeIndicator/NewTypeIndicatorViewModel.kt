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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class NewTypeIndicatorViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val addTypeIndicatorUseCase: AddTypeIndicatorUseCase,
): ViewModel() {

    private val _selectedImages = MutableLiveData<List<Uri?>>()
    val selectedImages: LiveData<List<Uri?>>
        get() = _selectedImages

    private val _resultSave = MutableLiveData<StateSave>()
    val resultSave: LiveData<StateSave>
        get() = _resultSave

    fun setImages(list: List<Uri>){
        _selectedImages.value = list
    }

    //id usera
    fun save(context: Context, typeName: String){
        val listId = mutableListOf<Int>()
        if(typeName.isEmpty() || selectedImages.value == null){
            _resultSave.value = StateSave.EMPTYFIELD
            return
        }
        viewModelScope.launch {
            for(el in selectedImages.value!!) {
                val tempFile = createTempFileFromUri(el!!, context)
                tempFile?.let { file ->
                    listId.add(uploadImageUseCase(file))
                }
            }
            val result = addTypeIndicatorUseCase(1, typeName, listId)
            _resultSave.value = if (result) StateSave.SAVED else StateSave.NOSAVED
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