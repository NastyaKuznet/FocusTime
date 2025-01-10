package com.example.focustime.data.network.entities.request

data class AddTypeIndicatorBody(
    val id: Int,
    val typeName: String,
    val images: List<Int>,
)