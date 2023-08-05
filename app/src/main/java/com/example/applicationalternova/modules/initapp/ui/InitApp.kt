package com.example.applicationalternova.modules.initapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationalternova.databinding.InitAppActivityBinding

class InitApp : AppCompatActivity() {

    private lateinit var binding: InitAppActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitAppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
