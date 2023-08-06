package com.example.applicationalternova.modules.signin.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.common.utils.Event
import com.example.applicationalternova.modules.signin.data.SingInDataSource
import com.example.applicationalternova.modules.signin.data.SingInRepository
import com.example.applicationalternova.modules.signin.domain.ISingInInteractor
import com.example.applicationalternova.modules.signin.domain.SingInInteractor
import com.example.applicationalternova.modules.signin.model.SignInViewState
import com.example.applicationalternova.modules.signin.model.UserSignInModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SingInViewModel : ViewModel() {

    private val interactor: ISingInInteractor =
        SingInInteractor(
            repository = SingInRepository(
                dataSource = SingInDataSource(
                    FirebaseAuthManager(),
                ),
            ),
        )

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>>
        get() = _navigateToLogin

    private val _navigateToVerifyEmail = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyEmail: LiveData<Event<Boolean>>
        get() = _navigateToVerifyEmail

    private val _viewState = MutableStateFlow(SignInViewState())
    val viewState: StateFlow<SignInViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    fun onSignInSelected(userSignIn: UserSignInModel) {
        val viewState = userSignIn.toSignInViewState()
        if (viewState.userValidated() && userSignIn.isNotEmpty()) {
            signInUser(userSignIn)
        } else {
            onFieldsChanged(userSignIn)
        }
    }

    private fun signInUser(userSignIn: UserSignInModel) {
        viewModelScope.launch {
            _viewState.value = SignInViewState(isLoading = true)
            val accountCreated = interactor.createAccount(userSignIn)
            if (accountCreated) {
                _navigateToVerifyEmail.value = Event(true)
            } else {
                _showErrorDialog.value = true
            }
            _viewState.value = SignInViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(userSignIn: UserSignInModel) {
        _viewState.value = userSignIn.toSignInViewState()
    }

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    private fun isValidOrEmptyEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidOrEmptyPassword(password: String, passwordConfirmation: String): Boolean =
        (password.length >= MIN_PASSWORD_LENGTH && password == passwordConfirmation) || password.isEmpty() || passwordConfirmation.isEmpty()

    private fun isValidName(name: String): Boolean =
        name.length >= MIN_PASSWORD_LENGTH || name.isEmpty()

    private fun UserSignInModel.toSignInViewState(): SignInViewState {
        return SignInViewState(
            isValidEmail = isValidOrEmptyEmail(email),
            isValidPassword = isValidOrEmptyPassword(password, passwordConfirmation),
            isValidNickName = isValidName(nickName),
            isValidRealName = isValidName(realName),
        )
    }
}
