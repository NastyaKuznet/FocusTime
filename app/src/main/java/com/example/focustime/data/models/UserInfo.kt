package com.example.focustime.data.models

data class UserInfo(
    val nickname: String,
    val status: String,
    val id_avatar: Int,
    val friends_count: Int,
    val total_focus_time: Int
)
