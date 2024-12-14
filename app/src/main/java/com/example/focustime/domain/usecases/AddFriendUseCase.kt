package com.example.focustime.domain.usecases

import com.example.focustime.data.network.entities.ResultFriends
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface AddFriendUseCase {

    suspend operator fun invoke(userId: Int): ResultFriends
}

class AddFriendUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): AddFriendUseCase {

    override suspend fun invoke(userId: Int): ResultFriends {
        return remoteDatabaseRepository.getRequest(userId)
    }
}