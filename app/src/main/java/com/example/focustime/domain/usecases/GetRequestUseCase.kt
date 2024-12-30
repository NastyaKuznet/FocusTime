package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import com.example.focustime.presentation.friends.Friend
import javax.inject.Inject

interface GetRequestUseCase  {

    suspend operator fun invoke(userId: Int): StateResponse<List<Friend>>
}

class GetRequestUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): GetRequestUseCase {

    override suspend fun invoke(userId: Int): StateResponse<List<Friend>> {
        return remoteDatabaseRepository.getRequest(userId)
    }
}