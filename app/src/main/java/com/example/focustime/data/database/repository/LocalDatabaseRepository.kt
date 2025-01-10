package com.example.focustime.data.database.repository

import com.example.focustime.data.State
import com.example.focustime.data.StateResponse
import com.example.focustime.data.database.dao.ImageDAO
import com.example.focustime.data.database.dao.IndicatorDAO
import com.example.focustime.data.database.dao.TypeIndicatorDAO
import com.example.focustime.data.database.model.ImageEntity
import com.example.focustime.data.database.model.IndicatorEntity
import com.example.focustime.data.database.model.TypeIndicatorEntity
import javax.inject.Inject

interface LocalDatabaseRepository {

    suspend fun addIndicator(interval: Int, day: String, idType: Int): StateResponse<Unit>
    suspend fun deleteAllIndicators(): StateResponse<Unit>
    suspend fun getAllIndicators(): StateResponse<List<IndicatorEntity>>

    suspend fun addImage(path: String, idType: Int): StateResponse<Unit>
    suspend fun getImagesByIdType(idType: Int): StateResponse<List<ImageEntity>>
    suspend fun deleteImagesByIdType(idType: Int): StateResponse<Unit>

    suspend fun addTypeIndicator(name: String): StateResponse<Int>
    suspend fun deleteTypeIndicator(idType: Int): StateResponse<Unit>
    suspend fun getAllTypesIndicator(): StateResponse<List<TypeIndicatorEntity>>
}

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val indicatorDAO: IndicatorDAO,
    private val imageDAO: ImageDAO,
    private val typeIndicatorDAO: TypeIndicatorDAO,
): LocalDatabaseRepository {

    override suspend fun addIndicator(interval: Int, day: String, idType: Int): StateResponse<Unit> {
        try {
            indicatorDAO.addIndicator(
                IndicatorEntity(
                interval=interval,
                day=day,
                idType = idType)
            )
            return StateResponse(
                State.SUCCESS,
                "Индикатор вставлен в таблицу",
                Unit)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }

    override suspend fun deleteAllIndicators(): StateResponse<Unit> {
        try {
            indicatorDAO.deleteAllHistoryIndicators()
            return StateResponse(
                State.SUCCESS,
                "Все индикаторы удалены из таблицы",
                Unit)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }

    override suspend fun getAllIndicators(): StateResponse<List<IndicatorEntity>> {
        try {
            val result = indicatorDAO.getAllHistoryIndicators()
            return StateResponse(
                State.SUCCESS,
                "Индикаторы получены из таблицы",
                result)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                listOf()
            )
        }
    }


    override suspend fun addImage(path: String, idType: Int): StateResponse<Unit> {
        try {
            val result = imageDAO.addImage(
                ImageEntity(
                path = path,
                idType = idType
                )
            )
            return StateResponse(
                State.SUCCESS,
                "Изображение сохраненно в таблице",
                result)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }

    override suspend fun getImagesByIdType(idType: Int): StateResponse<List<ImageEntity>> {
        try {
            val result = imageDAO.getImages(idType)
            return StateResponse(
                State.SUCCESS,
                "Изображения получены из таблицы",
                result)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                listOf()
            )
        }
    }

    override suspend fun deleteImagesByIdType(idType: Int): StateResponse<Unit> {
        try {
            val result = imageDAO.deleteImages(idType)
            return StateResponse(
                State.SUCCESS,
                "Изображения удалены из таблицы",
                result)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }


    override suspend fun addTypeIndicator(name: String): StateResponse<Int> {
        try {
            val result = typeIndicatorDAO.addTypeIndicator(
                TypeIndicatorEntity(
                name = name
            )
            )
            return StateResponse(
                State.SUCCESS,
                "Индикатор сохранен",
                result.toInt())
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                0
            )
        }
    }

    override suspend fun deleteTypeIndicator(idType: Int): StateResponse<Unit> {
        try {
            val result = typeIndicatorDAO.deleteTypeIndicator(idType)
            return StateResponse(
                State.SUCCESS,
                "Индикатор удален из таблицы",
                result)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                Unit
            )
        }
    }

    override suspend fun getAllTypesIndicator(): StateResponse<List<TypeIndicatorEntity>> {
        try {
            val result = typeIndicatorDAO.getAllTypesIndicators()
            return StateResponse(
                State.SUCCESS,
                "Индикаторы получены из таблицы",
                result)
        } catch (e: Exception) {
            return StateResponse(
                State.FAIL,
                "Ошибка: ${e.message}",
                listOf()
            )
        }
    }
}