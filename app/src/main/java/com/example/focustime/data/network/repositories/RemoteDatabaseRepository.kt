package com.example.focustime.data.network.repositories

import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.data.network.entities.request.AddTypeIndicatorBody
import com.example.focustime.data.network.entities.request.UserAuthAndRegistrationRequest
import com.example.focustime.data.network.services.RemoteDatabaseService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

interface RemoteDatabaseRepository {

    suspend fun registrationUser(nickname: String, password: String): ResultUser
    suspend fun authorizationUser(nickname: String, password: String): ResultUser
    suspend fun addTypeIndicator(id: Int, typeName: String, images: List<Int>): Boolean
    suspend fun uploadImage(file: File): Int
}

class RemoteDatabaseRepositoryImpl @Inject constructor(
    private val service: RemoteDatabaseService,
): RemoteDatabaseRepository {

    override suspend fun registrationUser(nickname: String, password: String): ResultUser {
        try {
            val user = service.registrationUser(UserAuthAndRegistrationRequest(nickname, password))
            return ResultUser(user, true)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                return ResultUser(User(0, "", "", ""), false)
            }
            throw e
        }
    }

    override suspend fun authorizationUser(nickname: String, password: String): ResultUser {
        try {
            val user = service.authorizationUser(UserAuthAndRegistrationRequest(nickname, password))
            return ResultUser(user, true)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                return ResultUser(User(0, "", "", ""), false)
            }
            throw e
        }
    }

    override suspend fun addTypeIndicator(
        id: Int,
        typeName: String,
        images: List<Int>
    ): Boolean {
        try {
            service.addTypeIndicator(AddTypeIndicatorBody(id, typeName, images))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun uploadImage(file: File): Int {
        try {
            val requestBody =
                RequestBody.create(MediaType.parse("image/*"), file)
            val multipartBody =
                MultipartBody.Part.createFormData("image", file.name, requestBody)

            return service.uploadImage(multipartBody)
        } catch (e: HttpException) {
            if (e.code() == 500) {
                return -1
            }
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}