package com.example.focustime.data.database.repository

import com.example.focustime.data.State
import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.dao.UserInfoDAO
import com.example.focustime.data.database.model.UserInfoEntity
import javax.inject.Inject

interface LocalDatabaseRepository {

    suspend fun getUserInfo(): StateResponse<UserInfoEntity>
    suspend fun insertUserInfo(userId: Int): StateResponse<Unit>
    suspend fun deleteUserInfo(userId: Int): StateResponse<Unit>
}

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val userInfoDAO: UserInfoDAO,
): LocalDatabaseRepository {

    override suspend fun getUserInfo(): StateResponse<UserInfoEntity> {
        try {
            val userInfo = userInfoDAO.getLastUserInfo()
            return if(userInfo != null){
                StateResponse(
                    State.SUCCESS,
                    "Id получен",
                    userInfo)
            } else {
                StateResponse(
                    State.FAIL,
                    "Ошибка извлечения id",
                    UserInfoEntity(-1,-1)
                )
            }
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                UserInfoEntity(-1,-1)
            )
        }
    }

    override suspend fun insertUserInfo(userId: Int): StateResponse<Unit> {
        try {
            userInfoDAO.upsertUserInfo(UserInfoEntity(idUser=userId))
            return StateResponse(
                    State.SUCCESS,
                    "UserInfo вставлено в таблицу",
                    Unit)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }

    override suspend fun deleteUserInfo(userId: Int): StateResponse<Unit> {
        try {
            userInfoDAO.deleteUserInfo(UserInfoEntity(idUser=userId))
            return StateResponse(
                State.SUCCESS,
                "UserInfo вставлено в таблицу",
                Unit)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }
}