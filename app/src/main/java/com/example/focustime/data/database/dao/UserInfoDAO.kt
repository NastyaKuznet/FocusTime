package com.example.focustime.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.focustime.data.database.model.UserInfoEntity

@Dao
interface UserInfoDAO {

    @Upsert
    suspend fun upsertUserInfo(userInfo: UserInfoEntity)

    @Delete
    suspend fun deleteUserInfo(userInfo: UserInfoEntity)

    @Query("SELECT * FROM ${UserInfoEntity.TABLE_NAME} LIMIT 1")
    fun getLastUserInfo(): UserInfoEntity?

}