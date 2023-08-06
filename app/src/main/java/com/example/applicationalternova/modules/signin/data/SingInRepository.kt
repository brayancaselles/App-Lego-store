package com.example.applicationalternova.modules.signin.data

import android.util.Log
import com.example.applicationalternova.modules.signin.domain.ISingInRepository
import com.example.applicationalternova.modules.signin.model.UserSignInModel
import com.google.firebase.auth.AuthResult

class SingInRepository(val dataSource: ISingInDataSource) : ISingInRepository {

    override suspend fun createAccount(email: String, password: String): AuthResult? {
        val response = dataSource.createAccount(email, password)
        Log.d("RESPONSE CREATE ACCOUNT", "$response")
        return response
    }

    override suspend fun createUserTable(userSignIn: UserSignInModel): Boolean {
        val response = dataSource.createUserTable(userSignIn)
        Log.d("RESPONSE CREATE TABLE", "$response")
        return response
    }
}
