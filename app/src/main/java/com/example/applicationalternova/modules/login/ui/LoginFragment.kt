package com.example.applicationalternova.modules.login.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.applicationalternova.R
import com.example.applicationalternova.databinding.LoginFragmentBinding
import com.example.applicationalternova.modules.login.model.LoginViewState
import com.example.applicationalternova.modules.common.utils.dismissKeyboard
import com.example.applicationalternova.modules.common.utils.loseFocusAfterAction
import com.example.applicationalternova.modules.common.utils.onTextChanged
import com.example.applicationalternova.modules.login.model.UserLoginModel
import com.google.firebase.auth.FirebaseAuth

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

            textViewRegister.setOnClickListener { viewModel.onSignInSelected() }

            buttonLogin.setOnClickListener {
                it.dismissKeyboard()
                viewModel.onLoginSelected(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString(),
                )
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToHome.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToHome()
            }
        }

        viewModel.navigateToSignIn.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToSignIn()
            }
        }

        viewModel.navigateToVerifyAccount.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToVerify()
            }
        }

        viewModel.showErrorDialog.observe(viewLifecycleOwner) { userLogin ->
            if (userLogin.showErrorDialog) showErrorDialog(userLogin)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    private fun updateUI(viewState: LoginViewState) {
        with(binding) {
            progressBar.isVisible = viewState.isLoading
            editTextEmail.error =
                if (viewState.isValidEmail) null else getString(R.string.login_error_email)
            editTextPassword.error =
                if (viewState.isValidPassword) null else getString(R.string.login_error_password)
        }
    }

    private fun showErrorDialog(userLogin: UserLoginModel?) {
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.login_error_dialog_title))
            .setMessage(getString(R.string.login_error_dialog_message))
            .setPositiveButton(getString(R.string.sign_in_error_dialog_positive_action)) { view, _ ->
                if (userLogin != null) {
                    viewModel.onLoginSelected(
                        userLogin.email,
                        userLogin.password,
                    )
                }
                view.dismiss()
            }.setNegativeButton(android.R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun goToVerify() {
        findNavController().navigate(R.id.action_loginFragment_to_verificationFragment)
    }

    private fun goToSignIn() {
        findNavController().navigate(R.id.action_loginFragment_to_singInFragment)
    }

    private fun goToHome() {
        saveData()
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            viewModel.onFieldsChanged(
                email = binding.editTextEmail.text.toString(),
                password = binding.editTextPassword.text.toString(),
            )
        }
    }

    private fun saveData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val sharedPreferences =
            requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_uid", currentUser?.uid)
        editor.putString("user_email", currentUser?.email)
        editor.apply()
    }
}
