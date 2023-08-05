package com.example.applicationalternova.modules.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.applicationalternova.databinding.LoginFragmentBinding
import com.example.applicationalternova.modules.common.utils.dismissKeyboard
import com.example.applicationalternova.modules.common.utils.loseFocusAfterAction
import com.example.applicationalternova.modules.common.utils.onTextChanged

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        with(binding) {
            editTextEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
            editTextEmail.onTextChanged { onFieldChanged() }

            editTextPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
            editTextPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            editTextPassword.onTextChanged { onFieldChanged() }

            textViewRegister.setOnClickListener { viewModel }

            buttonLogin.setOnClickListener {
                it.dismissKeyboard()
                /*viewModel.onLoginSelected(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString(),
                )*/
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            /*loginViewModel.onFieldsChanged(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
            )*/
        }
    }
}
