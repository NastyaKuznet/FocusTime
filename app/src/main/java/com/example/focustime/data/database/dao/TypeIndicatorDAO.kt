package com.example.focustime.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.focustime.data.database.model.TypeIndicatorEntity

@Dao
interface TypeIndicatorDAO {

    @Insert
    suspend fun addTypeIndicator(typeIndicatorEntity: TypeIndicatorEntity): Long

    @Query("DELETE FROM ${TypeIndicatorEntity.TABLE_NAME_TYPE_INDICATOR} WHERE id = :idType")
    suspend fun deleteTypeIndicator(idType: Int)

    @Query("SELECT * FROM ${TypeIndicatorEntity.TABLE_NAME_TYPE_INDICATOR}")
    suspend fun getAllTypesIndicators(): List<TypeIndicatorEntity>
}