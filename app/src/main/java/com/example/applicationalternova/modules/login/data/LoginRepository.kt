package com.example.applicationalternova.modules.login.data

import com.example.applicationalternova.modules.login.domain.ILoginRepository

class LoginRepository(private val dataSource: ILoginDataSource) : ILoginRepository
