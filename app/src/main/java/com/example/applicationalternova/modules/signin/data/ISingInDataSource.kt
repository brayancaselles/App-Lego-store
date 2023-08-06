package com.example.applicationalternova.modules.signin.data

import com.example.applicationalternova.modules.signin.model.UserSignInModel
import com.google.firebase.auth.AuthResult

interface ISingInDataSource {

    suspend fun createAccount(email: String, password: String): AuthResult?

    suspend fun createUserTable(userSignIn: UserSignInModel): Boolean
}
