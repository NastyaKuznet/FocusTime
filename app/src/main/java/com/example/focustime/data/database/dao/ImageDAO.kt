package com.example.focustime.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.focustime.data.database.model.ImageEntity

@Dao
interface ImageDAO {

    @Insert
    suspend fun addImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ${ImageEntity.TABLE_NAME_IMAGE} WHERE idType = :typeId")
    suspend fun getImages(typeId: Int): List<ImageEntity>

    @Query("DELETE FROM ${ImageEntity.TABLE_NAME_IMAGE} WHERE idType = :typeId")
    suspend fun deleteImages(typeId: Int)
}