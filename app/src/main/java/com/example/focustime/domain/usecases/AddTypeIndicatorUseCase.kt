package com.example.focustime.domain.usecases

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface AddTypeIndicatorUseCase {
    suspend operator fun invoke(id: Int, typeName: String, images: List<Int>): Boolean
}

class AddTypeIndicatorUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): AddTypeIndicatorUseCase{

    override suspend fun invoke(id: Int, typeName: String, images: List<Int>): Boolean {
        return remoteDatabaseRepository.addTypeIndicator(id, typeName, images)
    }

}