package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.models.UserInfo
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface getUserInfoUseCase {

    suspend operator fun invoke(userId: Int): StateResponse<UserInfo>
}

class getUserInfoUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): getUserInfoUseCase {

    override suspend fun invoke(userId: Int): StateResponse<UserInfo> {
        return remoteDatabaseRepository.getUserInfo(userId)
    }
}