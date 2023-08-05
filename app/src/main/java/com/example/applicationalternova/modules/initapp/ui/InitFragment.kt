package com.example.applicationalternova.modules.initapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.InitFragmentBinding

class InitFragment : Fragment() {

    private var _binding: InitFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InitAppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = InitFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            buttonLogin.setOnClickListener { viewModel.onLoginSelected() }
            buttonSingIn.setOnClickListener { viewModel.onSignInSelected() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationToLogin.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { goToLogin() }
        }
        viewModel.navigateToSignIn.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { goToSingIn() }
        }
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.navigate_to_login)
    }

    private fun goToSingIn() {
        findNavController().navigate(R.id.navigate_to_sing_in)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
