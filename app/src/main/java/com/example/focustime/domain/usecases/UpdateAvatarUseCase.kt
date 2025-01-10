package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface UpdateAvatarUseCase {

    suspend operator fun invoke(userId: Int, newAvatarId: Int): StateResponse<Unit>
}

class UpdateAvatarUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): UpdateAvatarUseCase {

    override suspend fun invoke(userId: Int, newAvatarId: Int): StateResponse<Unit> {
        return remoteDatabaseRepository.updateAvatar(userId, newAvatarId)
    }
}