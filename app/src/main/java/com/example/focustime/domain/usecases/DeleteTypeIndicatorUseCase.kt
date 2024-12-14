package com.example.focustime.domain.usecases

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface DeleteTypeIndicatorUseCase {

    suspend operator fun invoke(idType: Int): Boolean
}

class DeleteTypeIndicatorUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): DeleteTypeIndicatorUseCase{

    override suspend fun invoke(idType: Int): Boolean {
        return remoteDatabaseRepository.deleteTypeIndicator(idType)
    }

}