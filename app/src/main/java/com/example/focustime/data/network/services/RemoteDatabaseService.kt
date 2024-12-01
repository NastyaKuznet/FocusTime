package com.example.focustime.data.network.services

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.request.*
import com.example.focustime.presentation.friends.Friend
import retrofit2.http.*

interface RemoteDatabaseService {

    @POST("/authorization")
    suspend fun authorizationUser(@Body requestBody: UserAuthAndRegistrationRequest): User

    @POST("/registration")
    suspend fun registrationUser(@Body requestBody: UserAuthAndRegistrationRequest): User

    @POST("/allfriend")
    suspend fun getFriends(@Body requestBody: GetFriendsRequest): List<Friend>

    @POST("/request")
    suspend fun getRequest(@Body requestBody: GetFriendsRequest): List<Friend>
}