package com.example.applicationalternova.modules.login.data

import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.login.model.LoginResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class LoginDataSource(private val firebase: FirebaseAuthManager) : ILoginDataSource {

    override suspend fun login(email: String, password: String): LoginResult = runCatching {
        firebase.auth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    private fun Result<AuthResult>.toLoginResult() = when (val result = getOrNull()) {
        null -> LoginResult.Error
        else -> {
            val userId = result.user
            checkNotNull(userId)
            LoginResult.Success(result.user?.isEmailVerified ?: false)
        }
    }
}
