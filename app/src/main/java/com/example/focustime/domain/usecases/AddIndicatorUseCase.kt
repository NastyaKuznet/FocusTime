package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface AddIndicatorUseCase {
    suspend operator fun invoke(userId: Int, interval: Int, type: Int, date: String): StateResponse<Unit>
}

class AddIndicatorUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): AddIndicatorUseCase {

    override suspend fun invoke(userId: Int, interval: Int, type: Int, date: String): StateResponse<Unit> {
        return remoteDatabaseRepository.addIndicator(userId, interval, type, date)
    }
}
