package com.example.applicationalternova.modules.signin.domain

import com.example.applicationalternova.modules.signin.model.UserSignInModel

class SingInInteractor(val repository: ISingInRepository) : ISingInInteractor {

    override suspend fun createAccount(userSignIn: UserSignInModel): Boolean {
        return repository.createAccount(userSignIn)
    }
}
