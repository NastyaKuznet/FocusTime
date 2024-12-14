package com.example.focustime.data.network.repositories

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.ResultFriends
import com.example.focustime.data.network.entities.ResultSendFriendRequest
import com.example.focustime.presentation.models.ResultUI
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.data.network.entities.request.GetFriendsRequest
import com.example.focustime.data.network.entities.request.SendFriendRequest
import com.example.focustime.data.network.entities.request.UserAuthAndRegistrationRequest
import com.example.focustime.data.network.services.RemoteDatabaseService
import retrofit2.HttpException
import javax.inject.Inject

interface RemoteDatabaseRepository {

    suspend fun registrationUser(nickname: String, password: String): ResultUser
    suspend fun authorizationUser(nickname: String, password: String): ResultUser
    suspend fun getFriends(userId: Int): ResultFriends
    suspend fun getRequest(userId: Int): ResultFriends
    suspend fun addFriend():Unit
    suspend fun sendFriendRequest(user1Id: Int, user2Nickname: String): ResultSendFriendRequest
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


    override suspend fun getFriends(userId: Int): ResultFriends {
        try {
            val friends = service.getFriends(GetFriendsRequest(userId))
            return ResultFriends(friends, true)
        } catch (e: HttpException){
            if (e.code() == 404){
                return ResultFriends(emptyList(), false)
            }
            throw e
        }
    }

    override suspend fun getRequest(userId: Int): ResultFriends {
        try {
            val request = service.getRequest(GetFriendsRequest(userId))
            return ResultFriends(request, true)
        } catch (e: HttpException){
            if (e.code() == 404){
                return ResultFriends(emptyList(), false)
            }
            throw e
        }
    }

    override suspend fun addFriend(){

    }

    override suspend fun sendFriendRequest(user1Id: Int, user2Nickname: String): ResultSendFriendRequest {
        try {
            service.sendFriendRequest(SendFriendRequest(user1Id, user2Nickname))
            return ResultSendFriendRequest(true)
        } catch (e: HttpException) {
            if (e.code() == 500) {
                return ResultSendFriendRequest(false)
            }
            if (e.code() == 409) {
                return ResultSendFriendRequest(false)
            }
            if (e.code() == 404) {
                return ResultSendFriendRequest(false)
            }
            throw e
        }
    }
}