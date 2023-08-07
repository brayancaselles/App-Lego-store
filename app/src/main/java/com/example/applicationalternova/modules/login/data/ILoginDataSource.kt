package com.example.applicationalternova.modules.login.data

import com.example.applicationalternova.modules.login.model.LoginResult

interface ILoginDataSource {

    suspend fun login(email: String, password: String): LoginResult
}
