package com.example.focustime.data.network.repositories

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.data.network.entities.request.UserAuthAndRegistrationRequest
import com.example.focustime.data.network.services.RemoteDatabaseService
import retrofit2.HttpException
import javax.inject.Inject

interface RemoteDatabaseRepository {

    suspend fun registrationUser(nickname: String, password: String): ResultUser
    suspend fun authorizationUser(nickname: String, password: String): ResultUser
}

class RemoteDatabaseRepositoryImpl @Inject constructor(
    private val service: RemoteDatabaseService,
): RemoteDatabaseRepository {

    override suspend fun registrationUser(nickname: String, password: String): ResultUser {
        try {
            val user = service.registrationUser(UserAuthAndRegistrationRequest(nickname, password))
            return ResultUser(user, true)
        } catch (e: HttpException){
            if (e.code() == 404){
                return ResultUser(User(0,"","",""), false)
            }
            throw e
        }
    }

    override suspend fun authorizationUser(nickname: String, password: String): ResultUser {
        try {
            val user = service.authorizationUser(UserAuthAndRegistrationRequest(nickname, password))
            return ResultUser(user, true)
        } catch (e: HttpException){
            if (e.code() == 404){
                return ResultUser(User(0,"","",""), false)
            }
            throw e
        }
    }

}