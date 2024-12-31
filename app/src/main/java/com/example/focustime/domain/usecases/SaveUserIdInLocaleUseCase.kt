package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import javax.inject.Inject

interface SaveUserIdInLocaleUseCase {

    suspend operator fun invoke(userId: Int): StateResponse<Unit>
}

class SaveUserIdInLocaleUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): SaveUserIdInLocaleUseCase {

    override suspend fun invoke(userId: Int): StateResponse<Unit> {
        return localDatabaseRepository.insertUserInfo(userId)
    }
}