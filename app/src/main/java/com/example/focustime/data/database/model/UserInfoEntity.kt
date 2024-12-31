package com.example.focustime.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.focustime.data.database.model.UserInfoEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class UserInfoEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idUser: Int,
){
    companion object {
        const val TABLE_NAME = "userInfo"
    }
}