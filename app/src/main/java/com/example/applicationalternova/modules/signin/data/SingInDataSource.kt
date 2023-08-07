package com.example.applicationalternova.modules.signin.data

import android.util.Log
import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.signin.model.UserSignInModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await

class SingInDataSource(private val firebase: FirebaseAuthManager) : ISingInDataSource {

    companion object {
        const val USER_COLLECTION = "users"
    }

    override suspend fun createAccount(userSignIn: UserSignInModel): Boolean {
        return try {
            firebase.auth.createUserWithEmailAndPassword(userSignIn.email, userSignIn.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebase.auth.currentUser?.uid.toString()
                        createUserTable(userId, userSignIn)
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthUserCollisionException) {
                            Log.d("AUTHRESULTERROR", "El correo electrónico ya está registrado.")
                        } else {
                            Log.d("AUTHRESULTERROR", "Error al crear el usuario.")
                        }
                    }
                }.await()
            true
        } catch (e: Exception) {
            Log.d("AUTHRESULTERROR", "Error al crear el usuario. --> $e")
            false
        }
    }

    private fun createUserTable(userId: String, userSignIn: UserSignInModel): Boolean {
        var isCreateUserTable = false
        val userDocument = firebase.db.collection(USER_COLLECTION).document(userId)
        val userData = hashMapOf(
            "email" to userSignIn.email,
            "nickname" to userSignIn.nickName,
            "realname" to userSignIn.realName,
        )

        userDocument.set(userData).addOnSuccessListener {
            firebase.isUserAuth = true
            isCreateUserTable = true
            Log.d("AUTHRESULTSUCCESS", "registro exitoso")
        }.addOnFailureListener { exception ->
            isCreateUserTable = false
            Log.d("AUTHRESULTERROR", "fallo la creacion en la tabla $exception")
        }

        return isCreateUserTable
    }
}
