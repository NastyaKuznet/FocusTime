package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface AcceptRequestUseCase  {

    suspend operator fun invoke(userId1: Int, userId2: Int): StateResponse<Unit>
}

class AcceptRequestUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): AcceptRequestUseCase {

    override suspend fun invoke(userId1: Int, userId2: Int): StateResponse<Unit> {
        return remoteDatabaseRepository.acceptRequest(userId1, userId2)
    }
}