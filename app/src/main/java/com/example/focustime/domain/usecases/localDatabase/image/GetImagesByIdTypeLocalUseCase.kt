package com.example.focustime.domain.usecases.localDatabase.image

import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.repository.LocalDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject

interface GetImagesByIdTypeLocalUseCase {

    suspend operator fun invoke(idType: Int): StateResponse<List<InputStream>>
}

class GetImagesByIdTypeLocalUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
): GetImagesByIdTypeLocalUseCase {

    override suspend fun invoke(idType: Int): StateResponse<List<InputStream>> {
        val resultLocal = localDatabaseRepository.getImagesByIdType(idType)
        val listInputStreams = mutableListOf<InputStream>()
        for(el in resultLocal.content){
            val file = File(el.path)
            val inputStream = withContext(Dispatchers.IO) {
                FileInputStream(file)
            }
            listInputStreams.add(inputStream)
        }
        return StateResponse(resultLocal.state, resultLocal.message, listInputStreams)
    }
}