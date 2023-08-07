package com.example.applicationalternova.modules.common.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseAuthManager() {

    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    var isUserAuth = false

    fun getUserId(): String = auth.currentUser?.uid.toString()

    fun getEmailUser(): String = auth.currentUser?.email.orEmpty()

    val token = auth.currentUser
}
