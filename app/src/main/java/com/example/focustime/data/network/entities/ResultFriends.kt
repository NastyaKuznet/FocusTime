package com.example.focustime.data.network.entities

import com.example.focustime.presentation.friends.Friend

data class ResultFriends(
    val friends: List<Friend>,
    val success: Boolean
)