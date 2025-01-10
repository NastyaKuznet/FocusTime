package com.example.focustime.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.focustime.data.database.model.IndicatorEntity.Companion.TABLE_NAME_INDICATOR

@Entity(tableName = TABLE_NAME_INDICATOR)
data class IndicatorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val interval: Int,
    val day: String,
    val idType: Int,
) {

    companion object {
        const val TABLE_NAME_INDICATOR = "indicatorEntity"
    }
}