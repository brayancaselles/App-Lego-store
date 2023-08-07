package com.example.applicationalternova.modules.home.data

import android.util.Log
import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.common.retrofit.ApiService
import com.example.applicationalternova.modules.home.model.ProductsModel
import kotlinx.coroutines.tasks.await

class HomeDataSource(private val network: ApiService, private val firebase: FirebaseAuthManager) :
    IHomeDataSource {

    companion object {
        const val USER_COLLECTION = "users"
    }

    override suspend fun getProducts(): ProductsModel? {
        val call = network.getAllProducts()
        return if (call.isSuccessful) {
            Log.d("RESPONSE LIST", "response --> ${call.body()}")
            call.body()
        } else {
            Log.d("RESPONSE ERROR LIST", "response failed")
            null
        }
    }

    override suspend fun getNameUser(): String? {
        return try {
            val userId = firebase.auth.currentUser?.uid
            Log.d("USERID ----> ", "$userId")
            var userName = ""
            if (userId != null) {
                firebase.db.collection(USER_COLLECTION).document(userId).get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            userName = documentSnapshot.getString("realname").toString()
                            Log.d("USERNAME ----> ", "$userName")
                        }
                    }.await()
            }
            userName
        } catch (e: Exception) {
            ""
        }
    }
}
