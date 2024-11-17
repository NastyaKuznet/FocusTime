package com.example.focustime.data.network.entities

import com.example.focustime.data.models.User

data class ResultUser(
    val user: User,
    val stateResult: Boolean
)
