package com.timizatechnologies.crm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.timizatechnologies.crm.helpers.FirebaseUserLiveData

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}
