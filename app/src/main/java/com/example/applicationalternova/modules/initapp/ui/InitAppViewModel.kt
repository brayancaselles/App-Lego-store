package com.example.applicationalternova.modules.initapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applicationalternova.modules.common.utils.Event

class InitAppViewModel() : ViewModel() {

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigationToLogin: LiveData<Event<Boolean>> get() = _navigateToLogin

    private val _navigateToSignIn = MutableLiveData<Event<Boolean>>()
    val navigateToSignIn: LiveData<Event<Boolean>> get() = _navigateToSignIn

    fun onLoginSelected() {
        _navigateToLogin.value = Event(true)
    }

    fun onSignInSelected() {
        _navigateToSignIn.value = Event(true)
    }
}
