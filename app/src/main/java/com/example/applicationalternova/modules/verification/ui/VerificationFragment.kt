package com.example.applicationalternova.modules.verification.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.VerificationFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class VerificationFragment : Fragment() {

    private var _binding: VerificationFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VerificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = VerificationFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            buttonToHome.setOnClickListener { viewModel.onGoToDetailSelected() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToVerifyAccount.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                saveSessionPreferences()
                findNavController().navigate(R.id.action_verificationFragment_to_homeFragment)
            }
        }

        viewModel.showContinueButton.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.buttonToHome.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveSessionPreferences() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("active", true)
        editor.apply()
        Log.d("SAVE", "SAVE session")
    }
}
