package com.example.focustime.domain.usecases

import com.example.focustime.data.network.entities.ResultFriends
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface GetFriendsUseCase {

    suspend operator fun invoke(userId: Int): ResultFriends
}

class GetFriendsUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): GetFriendsUseCase {

    override suspend fun invoke(userId: Int): ResultFriends {
        return remoteDatabaseRepository.getFriends(userId)
    }
}