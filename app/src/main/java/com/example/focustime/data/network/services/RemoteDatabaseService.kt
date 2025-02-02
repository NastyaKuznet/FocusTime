package com.example.focustime.data.network.services

import com.example.focustime.data.StateResponse
import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.request.*
import com.example.focustime.presentation.friends.Friend
import com.example.focustime.data.network.entities.response.IdImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RemoteDatabaseService {

    @POST("/authorization")
    suspend fun authorizationUser(@Body requestBody: UserAuthAndRegistrationRequest): Response<User>

    @POST("/registration")
    suspend fun registrationUser(@Body requestBody: UserAuthAndRegistrationRequest): Response<User>

    @POST("/add-type-indicator")
    suspend fun addTypeIndicator(@Body requestBody: AddTypeIndicatorBody): Response<Unit>

    @Multipart
    @POST("/upload-image")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<Int>

    @POST("/typesindicators")
    suspend fun getAllTypesIndicators(@Body requestBody: IdUserRequestBody): Response<List<TypeIndicator>>

    @POST("/get-image-id")
    suspend fun getImagesIds(@Body requestBody: IdTypeIndicatorRequestBody): Response<List<IdImageResponse>>

    @POST("/image")
    suspend fun getImage(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/delete-type-indicator")
    suspend fun deleteTypeIndicator(@Body requestBody: IdTypeIndicatorRequestBody): Response<Unit>

    @POST("/allindicators")
    suspend fun getAllIndicators(@Body requestBody: IdUserRequestBody): Response<List<Indicator>>

    @POST("/addindicator")
    suspend fun addIndicator(@Body requestBody: AddIndicatorBody): Response<Unit>

    @POST("/allfriend")
    suspend fun getFriends(@Body requestBody: GetFriendsRequest): Response<List<Friend>>

    @POST("/request")
    suspend fun getRequest(@Body requestBody: GetFriendsRequest): Response<List<Friend>>

    @PUT("/addFriend")
    suspend fun acceptRequest(@Body requestBody: AddFriendsRequest): Response<Unit>

    @PUT("/sendFriendRequest")
    suspend fun sendFriendRequest(@Body requestBody: SendFriendRequest): Response<Unit>

    @POST("/getUserInfo")
    suspend fun getUserInfo(@Body requestBody: IdUserRequestBody): Response<UserInfo>

    @PUT("/updateUserInfo")
    suspend fun updateUserInfo(@Body requestBody: UpdateUserInfoRequest): Response<Unit>

    @PUT("/updateAvatar")
    suspend fun updateAvatar(@Body requestBody: UpdateAvatarRequestBody): Response<Unit>
}