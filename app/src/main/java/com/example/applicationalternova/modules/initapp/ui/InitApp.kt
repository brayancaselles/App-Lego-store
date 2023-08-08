package com.example.applicationalternova.modules.initapp.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.InitAppActivityBinding
import com.google.firebase.auth.FirebaseUser

class InitApp : AppCompatActivity() {

    private lateinit var binding: InitAppActivityBinding
    private var token: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitAppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencias = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val destination: Int = if (preferencias.getBoolean("active", false)) {
            R.id.homeFragment
        } else {
            R.id.initFragment
        }

        setupNavigation(destination)
    }

    private fun setupNavigation(destination: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController

        navGraph.setStartDestination(destination)
        navController.graph = navGraph
        navController.navigate(destination)
    }
}
