package com.example.focustime.data.network.repositories

import com.example.focustime.data.network.entities.request.UserAuthAndRegistrationRequest
import com.example.focustime.data.network.services.RemoteDatabaseService
import retrofit2.HttpException
import javax.inject.Inject

interface RemoteDatabaseRepository {

    suspend fun registrationUser(nickname: String, password: String): Boolean
    suspend fun authorizationUser(nickname: String, password: String): Boolean
}

class RemoteDatabaseRepositoryImpl @Inject constructor(
    private val service: RemoteDatabaseService,
): RemoteDatabaseRepository {

    override suspend fun registrationUser(nickname: String, password: String): Boolean {
        try {
            service.registrationUser(UserAuthAndRegistrationRequest(nickname, password))
            return true
        } catch (e: HttpException){
            if (e.code() == 404){
                return false
            }
            throw e
        }
    }

    override suspend fun authorizationUser(nickname: String, password: String): Boolean {
        try {
            service.authorizationUser(UserAuthAndRegistrationRequest(nickname, password))
            return true
        } catch (e: HttpException){
            if (e.code() == 404){
                return false
            }
            throw e
        }
    }

}