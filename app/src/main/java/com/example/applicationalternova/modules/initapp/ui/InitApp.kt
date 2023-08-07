package com.example.applicationalternova.modules.initapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationalternova.databinding.InitAppActivityBinding
import com.google.firebase.auth.FirebaseUser

class InitApp : AppCompatActivity() {

    private lateinit var binding: InitAppActivityBinding
    private var token: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitAppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*token = FirebaseAuthManager().token

        if (token != null) {
            navigateToHome()
        }*/
    }

    /*private fun navigateToHome() {
        val fragment = HomeFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }*/
}
