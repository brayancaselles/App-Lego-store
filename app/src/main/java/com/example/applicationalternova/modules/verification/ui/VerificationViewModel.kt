package com.example.applicationalternova.modules.verification.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationalternova.modules.common.firebase.FirebaseAuthManager
import com.example.applicationalternova.modules.common.utils.Event
import com.example.applicationalternova.modules.verification.data.VerificationDataSource
import com.example.applicationalternova.modules.verification.data.VerificationRepository
import com.example.applicationalternova.modules.verification.domain.IVerificationInteractor
import com.example.applicationalternova.modules.verification.domain.VerificationInteractor
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class VerificationViewModel : ViewModel() {

    private val interactor: IVerificationInteractor =
        VerificationInteractor(
            repository = VerificationRepository(
                dataSource = VerificationDataSource(
                    FirebaseAuthManager(),
                ),
            ),
        )

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _showContinueButton = MutableLiveData<Event<Boolean>>()
    val showContinueButton: LiveData<Event<Boolean>>
        get() = _showContinueButton

    init {
        viewModelScope.launch { interactor.sendEmailVerificationUseCase() }
        viewModelScope.launch {

            interactor.verifyEmailUseCase()
                .catch {
                    Log.i("ERROR ---->", "Verification error: ${it.message}")
                }
                .collect { verification ->
                    if (verification) {
                        _showContinueButton.value = Event(verification)
                    }
                }
        }
    }

    fun onGoToDetailSelected() {
        _navigateToVerifyAccount.value = Event(true)
    }
}
