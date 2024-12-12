package com.example.focustime.data.network.repositories

import android.util.Log
import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.data.network.entities.request.*
import com.example.focustime.data.network.services.RemoteDatabaseService
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.InputStream
import javax.inject.Inject

interface RemoteDatabaseRepository {

    suspend fun registrationUser(nickname: String, password: String): ResultUser
    suspend fun authorizationUser(nickname: String, password: String): ResultUser
    suspend fun addTypeIndicator(id: Int, typeName: String, images: List<Int>): Boolean
    suspend fun uploadImage(file: File): Int
    suspend fun getAllTypeIndicators(idUser: Int): List<TypeIndicator>
    suspend fun getImagesIds(idType: Int): List<Int>
    suspend fun getImage(idImage: Int): InputStream?
    suspend fun deleteTypeIndicator(idType: Int): Boolean
    suspend fun addIndicator(userId: Int, interval: Int, type: Int, date: String): Boolean
    suspend fun getAllIndicators(userId: Int): List<Indicator>
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

    override suspend fun getAllTypeIndicators(idUser: Int): List<TypeIndicator> {
        try {
            return service.getAllTypesIndicators(IdUserRequestBody(idUser))
        } catch (e: Exception) {
            return listOf()
        }
    }

    override suspend fun getImagesIds(idType: Int): List<Int> {
        try {
            val result = service.getImagesIds(IdTypeIndicatorRequestBody(idType))
            val list = mutableListOf<Int>()
            for( el in result)
                list.add(el.idimage)
            return list
        } catch (e: Exception) {
            return listOf()
        }
    }

    override suspend fun getImage(idImage: Int): InputStream? {
        try {
            val imageRequest = IdImageRequestBody(idImage)
            val gson = Gson()
            val jsonBody = gson.toJson(imageRequest)
            val requestBody =
                RequestBody.create(MediaType.parse("application/json"), jsonBody)
            val response = service.getImage(requestBody)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val inputStream = responseBody.byteStream()
                    return inputStream
                } else {
                    Log.e("ImageLoad", "Response body is null")
                }
            } else {
                Log.e("ImageLoad", "Unsuccessful response code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
        return null
    }

    override suspend fun deleteTypeIndicator(idType: Int): Boolean {
        try {
            service.deleteTypeIndicator(IdTypeIndicatorRequestBody(idType))
            return true
        } catch (e: Exception) {
            throw e
            return false
        }
    }

    override suspend fun addIndicator(
        userId: Int,
        interval: Int,
        type: Int,
        date: String
    ): Boolean {
        try {
            service.addIndicator(AddIndicatorBody(userId, interval, type, date))
            return true
        } catch (e: Exception) {
            throw e
            return false
        }
    }

    override suspend fun getAllIndicators(userId: Int): List<Indicator> {
        try {
            return service.getAllIndicators(IdUserRequestBody(userId))
        } catch (e: Exception){
            throw e
        }
    }


}