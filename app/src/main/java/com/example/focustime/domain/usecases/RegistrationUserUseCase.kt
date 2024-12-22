package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.models.User
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface RegistrationUserUseCase {

    suspend operator fun invoke(nickname: String, password: String): StateResponse<User>
}

class RegistrationUserUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): RegistrationUserUseCase {

    override suspend fun invoke(nickname: String, password: String): StateResponse<User> {
        return remoteDatabaseRepository.registrationUser(nickname, password)
    }
}