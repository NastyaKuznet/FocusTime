package com.example.focustime.data.network.entities.request

data class AddIndicatorBody(
    val id_user: Int,
    val interval: Int,
    val type: Int,
    val date: String,
)
