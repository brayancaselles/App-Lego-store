package com.example.applicationalternova.modules.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.common.utils.Event
import com.example.applicationalternova.modules.login.model.LoginResult
import com.example.applicationalternova.modules.login.model.LoginViewState
import com.example.applicationalternova.modules.login.data.LoginDataSource
import com.example.applicationalternova.modules.login.data.LoginRepository
import com.example.applicationalternova.modules.login.domain.ILoginInteractor
import com.example.applicationalternova.modules.login.domain.LoginInteractor
import com.example.applicationalternova.modules.login.model.UserLoginModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val interactor: ILoginInteractor = LoginInteractor(
        repository = LoginRepository(
            dataSource = LoginDataSource(
                FirebaseAuthManager(),
            ),
        ),
    )

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>> get() = _navigateToHome

    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>> get() = _navigateToSignIn

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>> get() = _navigateToVerifyAccount

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> get() = _viewState

    private var _showErrorDialog = MutableLiveData(UserLoginModel())
    val showErrorDialog: LiveData<UserLoginModel> get() = _showErrorDialog

    fun onLoginSelected(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        } else {
            onFieldsChanged(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            when (val result = interactor.login(email, password)) {
                LoginResult.Error -> {
                    _showErrorDialog.value =
                        UserLoginModel(email = email, password = password, showErrorDialog = true)
                    _viewState.value = LoginViewState(isLoading = false)
                }

                is LoginResult.Success -> {
                    if (result.verified) {
                        _navigateToHome.value = Event(true)
                    } else {
                        _navigateToVerifyAccount.value = Event(true)
                    }
                }
            }
            _viewState.value = LoginViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(email: String, password: String) {
        _viewState.value = LoginViewState(
            isValidEmail = isValidEmail(email),
            isValidPassword = isValidPassword(password),
        )
    }

    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()
}
