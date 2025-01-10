package com.example.focustime.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.focustime.data.database.model.IndicatorEntity

@Dao
interface IndicatorDAO {

    @Query("SELECT * FROM ${IndicatorEntity.TABLE_NAME_INDICATOR}")
    suspend fun getAllHistoryIndicators(): List<IndicatorEntity>

    @Insert
    suspend fun addIndicator(indicatorEntity: IndicatorEntity)

    @Query("DELETE FROM ${IndicatorEntity.TABLE_NAME_INDICATOR}")
    suspend fun deleteAllHistoryIndicators()
}