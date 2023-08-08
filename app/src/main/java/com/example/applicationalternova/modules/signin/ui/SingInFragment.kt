package com.example.applicationalternova.modules.signin.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.applicationalternova.databinding.SingInFragmentBinding
import com.example.applicationalternova.modules.common.utils.dismissKeyboard
import com.example.applicationalternova.modules.common.utils.loseFocusAfterAction
import com.example.applicationalternova.modules.common.utils.onTextChanged
import com.example.applicationalternova.modules.signin.model.SignInViewState
import com.example.applicationalternova.modules.signin.model.UserSignInModel
import kotlinx.coroutines.launch

class SingInFragment : Fragment() {

    private var _binding: SingInFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SingInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SingInFragmentBinding.inflate(inflater, container, false)

        with(binding) {
            editTextEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
            editTextEmail.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            editTextEmail.onTextChanged { onFieldChanged() }

            editTextNickname.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
            editTextNickname.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            editTextNickname.onTextChanged { onFieldChanged() }

            editTextRealName.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
            editTextRealName.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            editTextRealName.onTextChanged { onFieldChanged() }

            editTextPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
            editTextPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            editTextPassword.onTextChanged { onFieldChanged() }

            editTextRepeatPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
            editTextRepeatPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            editTextRepeatPassword.onTextChanged { onFieldChanged() }

            buttonCreateAccount.setOnClickListener {
                it.dismissKeyboard()
                viewModel.onSignInSelected(
                    UserSignInModel(
                        realName = binding.editTextRealName.text.toString(),
                        nickName = binding.editTextNickname.text.toString(),
                        email = binding.editTextEmail.text.toString(),
                        password = binding.editTextPassword.text.toString(),
                        passwordConfirmation = binding.editTextRepeatPassword.text.toString(),
                    ),
                )
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToVerifyEmail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToVerifyEmail()
            }
        }

        viewModel.navigateToLogin.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        }

        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }

        viewModel.showErrorDialog.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                showErrorDialog(
                    getString(R.string.sign_in_error_dialog_title),
                    getString(R.string.sign_in_error_dialog_body),
                )
            }
        }

        viewModel.showExitsEmail.observe(viewLifecycleOwner) {
            if (it) {
                showErrorDialog(
                    getString(R.string.sign_in_error_dialog_title),
                    getString(R.string.sign_in_error_dialog_message),
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            viewModel.onFieldsChanged(
                UserSignInModel(
                    realName = binding.editTextRealName.text.toString(),
                    nickName = binding.editTextNickname.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextPassword.text.toString(),
                    passwordConfirmation = binding.editTextRepeatPassword.text.toString(),
                ),
            )
        }
    }

    private fun goToVerifyEmail() {
        findNavController().navigate(R.id.action_singInFragment_to_verificationFragment)
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.action_singInFragment_to_loginFragment)
    }

    private fun updateUI(viewState: SignInViewState) {
        with(binding) {
            progressBarLoading.isVisible = viewState.isLoading
            binding.editTextEmail.error =
                if (viewState.isValidEmail) null else getString(R.string.sign_in_error_mail)
            binding.editTextNickname.error =
                if (viewState.isValidNickName) null else getString(R.string.sign_in_error_nickname)
            binding.editTextRealName.error =
                if (viewState.isValidRealName) null else getString(R.string.sign_in_error_realname)
            binding.editTextPassword.error =
                if (viewState.isValidPassword) null else getString(R.string.sign_in_error_password)
            binding.editTextRepeatPassword.error =
                if (viewState.isValidPassword) null else getString(R.string.sign_in_error_password)
        }
    }

    private fun showErrorDialog(title: String, message: String) {
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.sign_in_error_dialog_positive_action)) { view, _ ->
                view.dismiss()
                findNavController().popBackStack()
            }.setNegativeButton(android.R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}
