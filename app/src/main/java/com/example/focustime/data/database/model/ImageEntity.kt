package com.example.focustime.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.focustime.data.database.model.ImageEntity.Companion.TABLE_NAME_IMAGE

@Entity(tableName = TABLE_NAME_IMAGE,
    foreignKeys = [
        ForeignKey(
            entity = TypeIndicatorEntity::class,
            parentColumns = ["id"],
            childColumns = ["idType"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val path: String,
    val idType: Int,
) {

    companion object {
        const val TABLE_NAME_IMAGE = "imageEntity"
    }
}