package com.example.applicationalternova.modules.verification.data

import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class VerificationDataSource(private val firebase: FirebaseAuthManager) :
    IVerificationDataSource {

    override suspend fun sendEmailVerificationUseCase() = runCatching {
        firebase.auth.currentUser?.sendEmailVerification()?.await() ?: false
    }.isSuccess

    override suspend fun verifiedAccount(): Flow<Boolean> = flow {
        while (true) {
            val verified = verifyEmailIsVerified()
            emit(verified)
            delay(1000)
        }
    }

    private suspend fun verifyEmailIsVerified(): Boolean {
        firebase.auth.currentUser?.reload()?.await()
        return firebase.auth.currentUser?.isEmailVerified ?: false
    }
}
