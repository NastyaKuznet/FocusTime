package com.example.focustime.domain.usecases.localDatabase.indicator

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import javax.inject.Inject

interface AddIndicatorLocalUseCase {

    suspend operator fun invoke(interval: Int, idType: Int, day: String): StateResponse<Unit>
}

class AddIndicatorLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): AddIndicatorLocalUseCase {

    override suspend fun invoke(interval: Int, idType: Int, day: String): StateResponse<Unit> {
        return localDatabaseRepository.addIndicator(interval, day, idType)
    }
}