package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.model.UserInfoEntity
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import javax.inject.Inject

interface GetUserIdLocaleUseCase {

    suspend operator fun invoke(): StateResponse<UserInfoEntity>
}

class GetUserIdLocaleUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): GetUserIdLocaleUseCase {

    override suspend fun invoke(): StateResponse<UserInfoEntity> {
        return localDatabaseRepository.getUserInfo()
    }
}