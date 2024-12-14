package com.example.focustime.presentation.models

import com.example.focustime.presentation.friends.Friend

data class ResultUIFriends(
    val friends: List<Friend>,
    val stateResult: ResultUIState
)