package com.example.focustime.domain.usecases

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface AuthorizationUserUseCase {

    suspend operator fun invoke(nickname: String, password: String): Boolean
}

class AuthorizationUserUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): AuthorizationUserUseCase {

    override suspend fun invoke(nickname: String, password: String): Boolean {
        return remoteDatabaseRepository.authorizationUser(nickname, password)
    }
}