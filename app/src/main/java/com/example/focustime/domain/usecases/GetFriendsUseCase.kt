package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.network.entities.ResultFriends
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import com.example.focustime.presentation.friends.Friend
import javax.inject.Inject

interface GetFriendsUseCase {

    suspend operator fun invoke(userId: Int): StateResponse<List<Friend>>
}

class GetFriendsUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): GetFriendsUseCase {

    override suspend fun invoke(userId: Int): StateResponse<List<Friend>> {
        return remoteDatabaseRepository.getFriends(userId)
    }
}