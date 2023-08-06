package com.example.applicationalternova.modules.verification.domain

import kotlinx.coroutines.flow.Flow

class VerificationInteractor(private val repository: IVerificationRepository) :
    IVerificationInteractor {

    override suspend fun sendEmailVerificationUseCase(): Boolean {
        return repository.sendEmailVerificationUseCase()
    }

    override suspend fun verifyEmailUseCase(): Flow<Boolean> {
        return repository.verifyEmailUseCase()
    }
}
