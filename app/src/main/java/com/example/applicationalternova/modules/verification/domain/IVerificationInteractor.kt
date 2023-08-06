package com.example.applicationalternova.modules.verification.domain

import kotlinx.coroutines.flow.Flow

interface IVerificationInteractor {

    suspend fun sendEmailVerificationUseCase(): Boolean

    suspend fun verifyEmailUseCase(): Flow<Boolean>
}
