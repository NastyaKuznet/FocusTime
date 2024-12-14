package com.example.focustime.domain.usecases

import com.example.focustime.data.models.Indicator
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface GetAllIndicatorsUseCase {
    suspend operator fun invoke(userId: Int):List<Indicator>
}

class GetAllIndicatorsUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): GetAllIndicatorsUseCase {

    override suspend fun invoke(userId: Int): List<Indicator> {
        return remoteDatabaseRepository.getAllIndicators(userId)
    }
}