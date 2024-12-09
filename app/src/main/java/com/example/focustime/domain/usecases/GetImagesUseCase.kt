package com.example.focustime.domain.usecases

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import java.io.InputStream
import javax.inject.Inject

interface GetImagesUseCase {

    suspend operator fun invoke(idType: Int): List<InputStream>
}

class GetImagesUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): GetImagesUseCase {

    override suspend fun invoke(idType: Int): List<InputStream> {
        val listImagesIds = remoteDatabaseRepository.getImagesIds(idType)
        val listImage = mutableListOf<InputStream>()
        for(id in listImagesIds){
            val result = remoteDatabaseRepository.getImage(id)
            if(result != null){
                listImage.add(result)
            }
        }
        return listImage
    }

}