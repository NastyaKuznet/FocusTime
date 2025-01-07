package com.example.focustime.domain.usecases.localDatabase.indicator

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import com.example.focustime.data.models.Indicator
import javax.inject.Inject

interface GetAllIndicatorsLocalUseCase {

    suspend operator fun invoke(): StateResponse<List<Indicator>>
}

class GetAllIndicatorsLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): GetAllIndicatorsLocalUseCase {

    override suspend fun invoke(): StateResponse<List<Indicator>> {
        val indicatorsEntities = localDatabaseRepository.getAllIndicators()
        val result = mutableListOf<Indicator>()
        for(el in indicatorsEntities.content){
            result.add(Indicator(el.id,
                el.interval,
                el.idType,
                el.day,
                0))
        }
        return StateResponse(indicatorsEntities.state, indicatorsEntities.message, result)
    }
}