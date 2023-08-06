package com.example.applicationalternova.modules.verification.data

import com.example.applicationalternova.modules.verification.domain.IVerificationRepository
import kotlinx.coroutines.flow.Flow

class VerificationRepository(private val dataSource: IVerificationDataSource) :
    IVerificationRepository {

    override suspend fun sendEmailVerificationUseCase(): Boolean {
        return dataSource.sendEmailVerificationUseCase()
    }

    override suspend fun verifyEmailUseCase(): Flow<Boolean> {
        return dataSource.verifiedAccount()
    }
}
