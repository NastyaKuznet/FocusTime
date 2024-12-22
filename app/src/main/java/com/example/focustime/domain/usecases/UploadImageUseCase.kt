package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import javax.inject.Inject
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import java.io.File

interface UploadImageUseCase {
    suspend operator fun invoke(file: File): StateResponse<Int>
}

class UploadImageUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository,
): UploadImageUseCase {

    override suspend fun invoke(file: File): StateResponse<Int> {
        return remoteDatabaseRepository.uploadImage(file)
    }

}
