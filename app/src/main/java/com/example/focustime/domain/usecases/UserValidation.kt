package com.example.focustime.domain.usecases


class UserValidation {

    fun validationRegistrationOrAuthorization(nickname: String, password: String): Boolean{
        val validNick =  Regex("^[a-zA-Z0-9]{1,10}$").matches(nickname)
        val validPass =  Regex("^[a-zA-Z0-9]{8,15}$").matches(nickname)
        return validNick && validPass
    }
    //nickname может быть длиною не более 10 символов, может состоять только из латинских букв и (или) цифр
    //пароль может быть длиною не менее 8 символов и не более 10 символов, может состоять только из латинских букв и (или) цифр
}