package com.example.applicationalternova.modules.login.domain

import com.example.applicationalternova.modules.login.model.LoginResult

interface ILoginRepository {

    suspend fun login(email: String, password: String): LoginResult
}
