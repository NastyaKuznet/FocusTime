package com.example.focustime.domain.usecases

import com.example.focustime.data.StateResponse
import com.example.focustime.data.models.User
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import javax.inject.Inject

interface AuthorizationUserUseCase {

    suspend operator fun invoke(nickname: String, password: String): StateResponse<User>
}

class AuthorizationUserUseCaseImpl @Inject constructor(
    private val remoteDatabaseRepository: RemoteDatabaseRepository
): AuthorizationUserUseCase {

    override suspend fun invoke(nickname: String, password: String): StateResponse<User> {
        return remoteDatabaseRepository.authorizationUser(nickname, password)
    }
}