package com.example.applicationalternova.modules.login.model

data class UserLoginModel(
    val email: String = "",
    val password: String = "",
    val showErrorDialog: Boolean = false,
)
