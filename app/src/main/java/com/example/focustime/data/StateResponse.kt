package com.example.focustime.data

data class StateResponse<T>(
    val state: State,
    val message: String,
    val content: T,
)

enum class State{
    SUCCESS,
    FAIL,
}