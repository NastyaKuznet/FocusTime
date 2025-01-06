package com.example.focustime.domain.usecases.localDatabase.typeIndicator

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import com.example.focustime.data.models.TypeIndicator
import javax.inject.Inject

interface GetTypesIndicatorLocalUseCase {

    suspend operator fun invoke(): StateResponse<List<TypeIndicator>>
}

class GetTypesIndicatorLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): GetTypesIndicatorLocalUseCase {

    override suspend fun invoke(): StateResponse<List<TypeIndicator>> {
        val resultLocal = localDatabaseRepository.getAllTypesIndicator()
        val listTypeIndicator = mutableListOf<TypeIndicator>()
        for(el in resultLocal.content){
            listTypeIndicator.add(TypeIndicator(el.id, el.name, 0))
        }
        return StateResponse(resultLocal.state, resultLocal.message, listTypeIndicator)
    }
}