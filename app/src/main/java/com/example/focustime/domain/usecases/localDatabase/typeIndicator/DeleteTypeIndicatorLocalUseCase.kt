package com.example.focustime.domain.usecases.localDatabase.typeIndicator

import com.example.focustime.data.State
import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.model.TypeIndicatorEntity
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import java.io.File
import javax.inject.Inject

interface DeleteTypeIndicatorLocalUseCase {

    suspend operator fun invoke(idType: Int): StateResponse<Unit>
}

class DeleteTypeIndicatorLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): DeleteTypeIndicatorLocalUseCase {

    override suspend fun invoke(idType: Int): StateResponse<Unit> {
        val resultImages = localDatabaseRepository.getImagesByIdType(idType)
        if(resultImages.state == State.FAIL){
            return StateResponse(resultImages.state, resultImages.message, Unit)
        }
        for(image in resultImages.content){
            val file = File(image.path)
            try {
                file.delete()
            } catch (e: Exception){
                return StateResponse(State.FAIL, "Ошибка удаления изображения: $e", Unit)
            }
        }
        val result = localDatabaseRepository.deleteTypeIndicator(idType)
        return result
    }
}