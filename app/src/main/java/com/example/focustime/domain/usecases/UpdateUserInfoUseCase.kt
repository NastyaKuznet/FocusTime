package com.example.focustime.domain.usecases

import com.example.focustime.data.models.UserInfo
import com.example.focustime.data.network.entities.ResultSendFriendRequest
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface UpdateUserInfoUseCase {

    suspend operator fun invoke(status: String, userId: Int): ResultSendFriendRequest
}

class UpdateUserInfoUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): UpdateUserInfoUseCase {

    override suspend fun invoke(status: String, userId: Int): ResultSendFriendRequest {
        return remoteDatabaseRepository.updateUserInfo(status, userId)
    }
}