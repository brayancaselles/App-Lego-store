package com.example.applicationalternova.modules.signin.data

import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.signin.model.UserSignInModel
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class SingInDataSource(private val firebase: FirebaseAuthManager) : ISingInDataSource {

    companion object {
        const val USER_COLLECTION = "users"
    }

    override suspend fun createAccount(email: String, password: String): AuthResult? {
        return firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun createUserTable(userSignIn: UserSignInModel) = runCatching {
        val user = hashMapOf(
            "email" to userSignIn.email,
            "nickname" to userSignIn.nickName,
            "realname" to userSignIn.realName,
        )

        firebase.db
            .collection(USER_COLLECTION)
            .add(user).await()
        firebase.isUserAuth = true
    }.isSuccess
}
