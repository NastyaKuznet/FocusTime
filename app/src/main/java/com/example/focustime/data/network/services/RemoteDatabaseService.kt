package com.example.focustime.data.network.services

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.request.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface RemoteDatabaseService {

    @POST("/authorization")
    suspend fun authorizationUser(@Body requestBody: UserAuthAndRegistrationRequest): User

    @POST("/registration")
    suspend fun registrationUser(@Body requestBody: UserAuthAndRegistrationRequest): User

    @POST("/add-type-indicator")
    suspend fun addTypeIndicator(@Body requestBody: AddTypeIndicatorBody)

    @Multipart
    @POST("/upload-image")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Int
}