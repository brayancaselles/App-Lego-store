package com.example.applicationalternova.modules.signin.domain

import com.example.applicationalternova.modules.signin.model.UserSignInModel

class SingInInteractor(val repository: ISingInRepository) : ISingInInteractor {

    override suspend fun createAccount(userSignIn: UserSignInModel): Boolean {
        val accountCreated = repository.createAccount(userSignIn.email, userSignIn.password) != null
        return if (accountCreated) {
            createUserTable(userSignIn)
        } else {
            false
        }
    }

    private suspend fun createUserTable(userSignIn: UserSignInModel): Boolean {
        return repository.createUserTable(userSignIn)
    }
}
