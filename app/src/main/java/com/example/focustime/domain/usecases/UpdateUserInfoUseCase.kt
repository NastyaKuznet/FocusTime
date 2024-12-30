package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.models.UserInfo
import com.example.focustime.data.network.entities.ResultSendFriendRequest
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface UpdateUserInfoUseCase {

    suspend operator fun invoke(status: String, userId: Int): StateResponse<Unit>
}

class UpdateUserInfoUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): UpdateUserInfoUseCase {

    override suspend fun invoke(status: String, userId: Int): StateResponse<Unit> {
        return remoteDatabaseRepository.updateUserInfo(status, userId)
    }
}