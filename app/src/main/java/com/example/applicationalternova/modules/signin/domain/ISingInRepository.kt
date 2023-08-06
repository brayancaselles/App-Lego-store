package com.example.applicationalternova.modules.signin.domain

import com.example.applicationalternova.modules.signin.model.UserSignInModel
import com.google.firebase.auth.AuthResult

interface ISingInRepository {

    suspend fun createAccount(email: String, password: String): AuthResult?

    suspend fun createUserTable(userSignIn: UserSignInModel): Boolean
}
