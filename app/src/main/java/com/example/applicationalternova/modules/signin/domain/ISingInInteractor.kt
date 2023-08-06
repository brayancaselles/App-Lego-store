package com.example.applicationalternova.modules.signin.domain

import com.example.applicationalternova.modules.signin.model.UserSignInModel

interface ISingInInteractor {

    suspend fun createAccount(userSignIn: UserSignInModel): Boolean
}
