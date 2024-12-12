package com.example.focustime.data.network.services

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.request.*
import com.example.focustime.data.network.entities.response.IdImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
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

    @POST("/typesindicators")
    suspend fun getAllTypesIndicators(@Body requestBody: IdUserRequestBody): List<TypeIndicator>

    @POST("/get-image-id")
    suspend fun getImagesIds(@Body requestBody: IdTypeIndicatorRequestBody): List<IdImageResponse>

    @POST("/image")
    suspend fun getImage(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/delete-type-indicator")
    suspend fun deleteTypeIndicator(@Body requestBody: IdTypeIndicatorRequestBody)

    @POST("/allindicators")
    suspend fun getAllIndicators(@Body requestBody: IdUserRequestBody): List<Indicator>

    @POST("/addindicator")
    suspend fun addIndicator(@Body requestBody: AddIndicatorBody)
}