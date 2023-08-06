package com.example.applicationalternova.modules.verification.data

import kotlinx.coroutines.flow.Flow

interface IVerificationDataSource {

    suspend fun sendEmailVerificationUseCase(): Boolean

    suspend fun verifiedAccount(): Flow<Boolean>
}
