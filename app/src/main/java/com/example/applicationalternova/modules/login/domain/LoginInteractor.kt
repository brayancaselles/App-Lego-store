package com.example.applicationalternova.modules.login.domain

import com.example.applicationalternova.modules.login.model.LoginResult

class LoginInteractor(private val repository: ILoginRepository) : ILoginInteractor {

    override suspend fun login(email: String, password: String): LoginResult {
        return repository.login(email, password)
    }
}
