package com.example.focustime.domain.usecases.localDatabase.indicator

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import javax.inject.Inject

interface DeleteAllIndicatorsLocalUseCase {

    suspend operator fun invoke(): StateResponse<Unit>
}

class DeleteAllIndicatorsLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): DeleteAllIndicatorsLocalUseCase {

    override suspend fun invoke(): StateResponse<Unit> {
        return localDatabaseRepository.deleteAllIndicators()
    }
}