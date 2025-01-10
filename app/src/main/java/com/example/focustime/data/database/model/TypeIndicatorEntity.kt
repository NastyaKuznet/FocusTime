package com.example.focustime.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.focustime.data.database.model.TypeIndicatorEntity.Companion.TABLE_NAME_TYPE_INDICATOR


@Entity(tableName = TABLE_NAME_TYPE_INDICATOR)
data class TypeIndicatorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
) {
    companion object {
        const val TABLE_NAME_TYPE_INDICATOR = "typeIndicatorEntity"
    }
}