package com.example.focustime.data.network.services

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.ResultSendFriendRequest
import com.example.focustime.data.network.entities.request.*
import com.example.focustime.presentation.friends.Friend
import com.example.focustime.data.network.entities.response.IdImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RemoteDatabaseService {

    //Response

    @POST("/authorization")
    suspend fun authorizationUser(@Body requestBody: UserAuthAndRegistrationRequest): User //L

    @POST("/registration")
    suspend fun registrationUser(@Body requestBody: UserAuthAndRegistrationRequest): User//N

    @POST("/add-type-indicator")
    suspend fun addTypeIndicator(@Body requestBody: AddTypeIndicatorBody)//N

    @Multipart
    @POST("/upload-image")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Int//N

    @POST("/typesindicators")
    suspend fun getAllTypesIndicators(@Body requestBody: IdUserRequestBody): List<TypeIndicator>//N

    @POST("/get-image-id")
    suspend fun getImagesIds(@Body requestBody: IdTypeIndicatorRequestBody): List<IdImageResponse>//N

    @POST("/image")
    suspend fun getImage(@Body requestBody: RequestBody): Response<ResponseBody>//N

    @POST("/delete-type-indicator")
    suspend fun deleteTypeIndicator(@Body requestBody: IdTypeIndicatorRequestBody)//N

    @POST("/allindicators")
    suspend fun getAllIndicators(@Body requestBody: IdUserRequestBody): List<Indicator>//N

    @POST("/addindicator")
    suspend fun addIndicator(@Body requestBody: AddIndicatorBody)//N

    @POST("/allfriend")
    suspend fun getFriends(@Body requestBody: GetFriendsRequest): List<Friend>//L

    @POST("/request")
    suspend fun getRequest(@Body requestBody: GetFriendsRequest): List<Friend>//L

    @POST("/addFriend")
    suspend fun addFriend(@Body requestBody: AddFriendsRequest)//L

    @PUT("/sendFriendRequest")
    suspend fun sendFriendRequest(@Body requestBody: SendFriendRequest)//L
}