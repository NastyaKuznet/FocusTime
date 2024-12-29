package com.example.focustime.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.focustime.data.database.dao.UserInfoDAO
import com.example.focustime.data.database.model.UserInfoEntity

@Database(
    entities = [
        UserInfoEntity::class,
    ],
    version = 1
)
abstract class LocalDatabase: RoomDatabase() {

    abstract val userInfoDao: UserInfoDAO

}