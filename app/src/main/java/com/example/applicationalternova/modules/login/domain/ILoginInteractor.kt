package com.example.applicationalternova.modules.login.domain

import com.example.applicationalternova.modules.login.model.LoginResult

interface ILoginInteractor {

    suspend fun login(email: String, password: String): LoginResult
}
