package com.example.focustime.domain.usecases

import com.example.focustime.data.network.entities.ResultSendFriendRequest
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface SendFriendRequestUseCase {

    suspend operator fun invoke(user1Id: Int, user2Nickname: String): ResultSendFriendRequest
}

class SendFriendRequestUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): SendFriendRequestUseCase {

    override suspend fun invoke(user1Id: Int, user2Nickname: String): ResultSendFriendRequest {
        return remoteDatabaseRepository.sendFriendRequest(user1Id, user2Nickname)
    }
}