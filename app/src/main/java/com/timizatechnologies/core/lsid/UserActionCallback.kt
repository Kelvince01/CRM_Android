package com.timizatechnologies.core.lsid

interface UserActionCallback {
    fun onAccountDelete();
    fun onChange();
    fun onLogin();
    fun onLogout();
    fun onRegistrationSuccess()
    fun onRequestTermsAccept()
}
