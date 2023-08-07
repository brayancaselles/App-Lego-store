package com.example.applicationalternova.modules.signin.data

import com.example.applicationalternova.modules.signin.model.UserSignInModel

interface ISingInDataSource {

    suspend fun createAccount(userSignIn: UserSignInModel): Boolean
}
