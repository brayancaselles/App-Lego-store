package com.example.applicationalternova.modules.signin.data

import com.example.applicationalternova.modules.signin.domain.ISingInRepository
import com.example.applicationalternova.modules.signin.model.UserSignInModel

class SingInRepository(val dataSource: ISingInDataSource) : ISingInRepository {

    override suspend fun createAccount(userSignIn: UserSignInModel): Boolean {
        return dataSource.createAccount(userSignIn)
    }
}
