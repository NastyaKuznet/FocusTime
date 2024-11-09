package com.example.focustime.domain.usecases

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface RegistrationUserUseCase {

    suspend operator fun invoke(nickname: String, password: String): Boolean
}

class RegistrationUserUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): RegistrationUserUseCase {

    override suspend fun invoke(nickname: String, password: String): Boolean {
        return remoteDatabaseRepository.registrationUser(nickname, password)
    }
}