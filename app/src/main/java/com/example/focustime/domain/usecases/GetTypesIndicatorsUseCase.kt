package com.example.focustime.domain.usecases

import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface GetTypesIndicatorsUseCase {

    suspend operator fun invoke(idUser: Int): List<TypeIndicator>
}

class GetTypesIndicatorsUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): GetTypesIndicatorsUseCase{

    override suspend fun invoke(idUser: Int): List<TypeIndicator> {
        return remoteDatabaseRepository.getAllTypeIndicators(idUser)
    }

}