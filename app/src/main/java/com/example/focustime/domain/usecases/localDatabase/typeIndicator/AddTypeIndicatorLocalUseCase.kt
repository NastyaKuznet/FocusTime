package com.example.focustime.domain.usecases.localDatabase.typeIndicator

import android.content.Context
import android.net.Uri
import com.example.focustime.data.State
import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

interface AddTypeIndicatorLocalUseCase {

    suspend operator fun invoke(context: Context, nameType: String, listUri: List<Uri?>): StateResponse<Unit>
}

class AddTypeIndicatorLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): AddTypeIndicatorLocalUseCase {

    override suspend fun invoke(context: Context, nameType: String, listUri: List<Uri?>): StateResponse<Unit> {
        val resultAddType = localDatabaseRepository.addTypeIndicator(nameType)
        if(resultAddType.state == State.FAIL){
            return StateResponse(resultAddType.state, resultAddType.message, Unit)
        }
        var count = 1
        for(uri in listUri){
            if(uri == null){
                return StateResponse(State.FAIL, "Ошибка получения uri изображения", Unit)
            }
            val fileName = "image_${count}_by_type_indicator_${nameType}_.jpg"
            count++
            val path = copyImageToInternalStorage(context, uri, fileName)
                ?: return StateResponse(State.FAIL, "Ошибка копирования изображения", Unit)
            val resultAddImage = localDatabaseRepository.addImage(path, resultAddType.content)
            if(resultAddImage.state == State.FAIL){
                return resultAddImage
            }
        }
        return StateResponse(resultAddType.state, resultAddType.message, Unit)
    }

    private fun copyImageToInternalStorage(context: Context, uri: Uri, fileName: String): String? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val internalDir = context.filesDir
        val outputFile = File(internalDir, fileName)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            return outputFile.absolutePath

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream.close()
                outputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace().toString()
            }
        }
    }
}