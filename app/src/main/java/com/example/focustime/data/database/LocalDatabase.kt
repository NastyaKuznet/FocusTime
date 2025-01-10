package com.example.focustime.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.focustime.data.database.dao.*
import com.example.focustime.data.database.model.*

@Database(
    entities = [
        IndicatorEntity::class,
        ImageEntity::class,
        TypeIndicatorEntity::class,
    ],
    version = 1
)
abstract class LocalDatabase: RoomDatabase() {

    abstract val indicatorDao: IndicatorDAO
    abstract val imageDao: ImageDAO
    abstract val typeIndicatorDAO: TypeIndicatorDAO
}