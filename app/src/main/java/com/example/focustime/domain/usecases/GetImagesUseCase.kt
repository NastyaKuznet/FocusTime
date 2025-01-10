package com.example.focustime.domain.usecases

import com.example.focustime.data.State
import com.example.focustime.data.StateResponse
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import java.io.InputStream
import javax.inject.Inject

interface GetImagesUseCase {

    suspend operator fun invoke(idType: Int): StateResponse<List<InputStream>>
}

class GetImagesUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): GetImagesUseCase {

    override suspend fun invoke(idType: Int): StateResponse<List<InputStream>> {
        val listImagesIds = remoteDatabaseRepository.getImagesIds(idType)
        if(listImagesIds.state == State.FAIL){
            return StateResponse(listImagesIds.state, listImagesIds.message, listOf())
        }
        val listImage = mutableListOf<InputStream>()
        for(id in listImagesIds.content){
            val result = remoteDatabaseRepository.getImage(id)
            if(result.state == State.SUCCESS && result.content != null){
                listImage.add(result.content)
            } else {
                return StateResponse(listImagesIds.state, result.message, listOf())
            }
        }
        return StateResponse(State.SUCCESS, "Изображения получены", listImage)
    }

}