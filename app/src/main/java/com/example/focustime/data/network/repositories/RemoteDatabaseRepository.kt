package com.example.focustime.data.network.repositories

import android.util.Log
import com.example.focustime.data.State
import com.example.focustime.data.StateResponse
import com.example.focustime.data.models.*
import com.example.focustime.data.network.entities.ResultFriends
import com.example.focustime.data.network.entities.ResultSendFriendRequest
import com.example.focustime.data.network.entities.ResultUser
import com.example.focustime.data.network.entities.request.*
import com.example.focustime.data.network.entities.request.GetFriendsRequest
import com.example.focustime.data.network.entities.request.SendFriendRequest
import com.example.focustime.data.network.entities.request.UserAuthAndRegistrationRequest
import com.example.focustime.data.network.services.RemoteDatabaseService
import com.example.focustime.presentation.UIState
import com.example.focustime.presentation.friends.Friend
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.InputStream
import javax.inject.Inject

interface RemoteDatabaseRepository {

    suspend fun registrationUser(nickname: String, password: String): StateResponse<User>
    suspend fun authorizationUser(nickname: String, password: String): StateResponse<User>
    suspend fun addTypeIndicator(id: Int, typeName: String, images: List<Int>): StateResponse<Unit>
    suspend fun uploadImage(file: File): StateResponse<Int>
    suspend fun getAllTypeIndicators(idUser: Int): StateResponse<List<TypeIndicator>>
    suspend fun getImagesIds(idType: Int): StateResponse<List<Int>>
    suspend fun getImage(idImage: Int): StateResponse<InputStream?>
    suspend fun deleteTypeIndicator(idType: Int): StateResponse<Unit>
    suspend fun addIndicator(userId: Int, interval: Int, type: Int, date: String): StateResponse<Unit>
    suspend fun getAllIndicators(userId: Int): StateResponse<List<Indicator>>
    suspend fun getFriends(userId: Int): StateResponse<List<Friend>>
    suspend fun getRequest(userId: Int): StateResponse<List<Friend>>
    suspend fun acceptRequest(userId1: Int, userId2: Int):StateResponse<Unit>
    suspend fun updateAvatar(userId: Int, newAvatarId: Int):StateResponse<Unit>
    suspend fun sendFriendRequest(user1Id: Int, user2Nickname: String): StateResponse<Unit>
    suspend fun getUserInfo(userId: Int): StateResponse<UserInfo>
    suspend fun updateUserInfo(status: String, userId: Int): StateResponse<Unit>
}

class RemoteDatabaseRepositoryImpl @Inject constructor(
    private val service: RemoteDatabaseService,
): RemoteDatabaseRepository {

    override suspend fun registrationUser(nickname: String, password: String): StateResponse<User> {
        try {
            val result = service.registrationUser(UserAuthAndRegistrationRequest(nickname, password))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Регистрация пройдена успешно",
                    result.body()!!)
            } else if(result.code() == 404) {
                StateResponse(State.FAIL,
                    "Пользователь с таким логином уже есть",
                    User(0,"","",""))
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает",
                    User(0,"","",""))
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}",
                    User(0,"","",""))
            }
        } catch (e: HttpException){
            return StateResponse(State.FAIL,
                "Ошибка: $e",
                User(0,"","",""))
        }
    }

    override suspend fun authorizationUser(nickname: String, password: String): StateResponse<User> {
        try {
            val result = service.authorizationUser(UserAuthAndRegistrationRequest(nickname, password))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Авторизация пройдена успешно",
                    result.body()!!)
            } else if(result.code() == 404) {
                StateResponse(State.FAIL,
                    "Такого пользователя нет",
                    User(0,"","",""))
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает",
                    User(0,"","",""))
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}",
                    User(0,"","",""))
            }
        } catch (e: HttpException){
            return StateResponse(State.FAIL,
                "Ошибка: $e",
                User(0,"","",""))
        }
    }

    override suspend fun addTypeIndicator(
        id: Int,
        typeName: String,
        images: List<Int>
    ): StateResponse<Unit> {
        try {
            val result = service.addTypeIndicator(AddTypeIndicatorBody(id, typeName, images))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Сохранено",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }

    override suspend fun uploadImage(file: File): StateResponse<Int> {
        try {
            val requestBody =
                RequestBody.create(MediaType.parse("image/*"), file)
            val multipartBody =
                MultipartBody.Part.createFormData("image", file.name, requestBody)

            val result = service.uploadImage(multipartBody)
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Сохранено",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", -1)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", -1)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", -1)
        }
    }

    override suspend fun getAllTypeIndicators(idUser: Int): StateResponse<List<TypeIndicator>> {
        try {
            val result = service.getAllTypesIndicators(IdUserRequestBody(idUser))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Типы индикаторов получены",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", listOf())
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", listOf())
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", listOf())
        }
    }

    override suspend fun getImagesIds(idType: Int): StateResponse<List<Int>> {
        try {
            val result = service.getImagesIds(IdTypeIndicatorRequestBody(idType))

            return if(result.isSuccessful && result.body() != null){
                val list = mutableListOf<Int>()
                for( el in result.body()!!)
                    list.add(el.idimage)
                StateResponse(
                    State.SUCCESS,
                    "Типы индикаторов получены", list)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", listOf())
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", listOf())
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", listOf())
        }
    }

    override suspend fun getImage(idImage: Int): StateResponse<InputStream?> {
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
                    return StateResponse(
                        State.SUCCESS,
                        "Типы индикаторов получены", inputStream)
                } else {
                    return StateResponse(State.FAIL,
                        "Изображение не загружено", null)
                }
            } else if(response.code() == 500) {
                return StateResponse(State.FAIL,
                    "Сервер не работает", null)
            } else {
                return StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${response.code()}", null)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", null)
        }
    }

    override suspend fun deleteTypeIndicator(idType: Int): StateResponse<Unit> {
        try {
            val result = service.deleteTypeIndicator(IdTypeIndicatorRequestBody(idType))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Удалено",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }

    override suspend fun addIndicator(
        userId: Int,
        interval: Int,
        type: Int,
        date: String
    ): StateResponse<Unit> {
        try {
            val result = service.addIndicator(AddIndicatorBody(userId, interval, type, date))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Идикатор сохранен",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }

    override suspend fun getAllIndicators(userId: Int): StateResponse<List<Indicator>> {
        try {
            val result = service.getAllIndicators(IdUserRequestBody(userId))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Список индикаторов получен",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", listOf())
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", listOf())
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", listOf()
            )
        }
    }



    override suspend fun getFriends(userId: Int): StateResponse<List<Friend>> {
        try {
            val result = service.getFriends(GetFriendsRequest(userId))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Друзья отображены",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сорвер не работает",
                    emptyList())
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}",
                    emptyList())
            }
        } catch (e: HttpException){
            return StateResponse(State.FAIL,
                "Ошибка: $e",
                emptyList())
        }
    }

    override suspend fun getRequest(userId: Int): StateResponse<List<Friend>> {
        try {
            val result = service.getRequest(GetFriendsRequest(userId))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Заявки отображены",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сорвер не работает",
                    emptyList())
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}",
                    emptyList())
            }
        } catch (e: HttpException){
            return StateResponse(State.FAIL,
                "Ошибка: $e",
                emptyList())
        }
    }

    override suspend fun acceptRequest(userId1: Int, userId2: Int):StateResponse<Unit>{
        try {
            val result = service.acceptRequest(AddFriendsRequest(userId1, userId2))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Заявка принята",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }

    override suspend fun updateAvatar(userId: Int, newAvatarId: Int):StateResponse<Unit>{
        try {
            val result = service.updateAvatar(UpdateAvatarRequestBody(userId, newAvatarId))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Аватарка сохранена",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }

    override suspend fun sendFriendRequest(user1Id: Int, user2Nickname: String): StateResponse<Unit> {
        try {
            val result = service.sendFriendRequest(SendFriendRequest(user1Id, user2Nickname))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Заявка отправлена",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else if(result.code() == 409) {
                StateResponse(State.FAIL,
                    "Заявка уже была отправлена", Unit)
            } else if(result.code() == 404) {
                StateResponse(State.FAIL,
                    "Такого пользователя нет", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }

    override suspend fun getUserInfo(userId: Int): StateResponse<UserInfo>{
        try {
            val result = service.getUserInfo(IdUserRequestBody(userId))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Аккаунт отображен",
                    result.body()!!)
            } else if(result.code() == 404) {
                StateResponse(State.FAIL,
                    "Такого пользователя нет",
                    UserInfo("","",0,0,0))
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает",
                    UserInfo("","",0,0,0))
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}",
                    UserInfo("","",0,0,0))
            }
        } catch (e: HttpException){
            return StateResponse(State.FAIL,
                "Ошибка: $e",
                UserInfo("","",0,0,0))
        }
    }

    override suspend fun updateUserInfo(status: String, userId: Int): StateResponse<Unit> {
        try {
            val result = service.updateUserInfo(UpdateUserInfoRequest(status, userId))
            return if(result.isSuccessful && result.body() != null){
                StateResponse(
                    State.SUCCESS,
                    "Статус сохранен",
                    result.body()!!)
            } else if(result.code() == 500) {
                StateResponse(State.FAIL,
                    "Сервер не работает", Unit)
            } else {
                StateResponse(State.FAIL,
                    "Сервер ответил кодом: ${result.code()}", Unit)
            }
        } catch (e: Exception) {
            return StateResponse(State.FAIL,
                "Ошибка: $e", Unit)
        }
    }
}