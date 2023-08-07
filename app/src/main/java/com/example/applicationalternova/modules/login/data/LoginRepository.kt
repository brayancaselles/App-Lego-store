package com.example.applicationalternova.modules.login.data

import com.example.applicationalternova.modules.login.model.LoginResult
import com.example.applicationalternova.modules.login.domain.ILoginRepository

class LoginRepository(private val dataSource: ILoginDataSource) : ILoginRepository {

    override suspend fun login(email: String, password: String): LoginResult {
        return dataSource.login(email, password)
    }
}
