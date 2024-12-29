package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import javax.inject.Inject

interface DeleteUserIdLocaleUseCase {

    suspend operator fun invoke(userId: Int): StateResponse<Unit>
}

class DeleteUserIdLocaleUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): DeleteUserIdLocaleUseCase {

    override suspend fun invoke(userId: Int): StateResponse<Unit> {
        return localDatabaseRepository.deleteUserInfo(userId)
    }
}