package com.example.searchapp.interfaces.login

interface LoginView {
    fun initVars()
    fun setListeners()
    fun checkAuthorization()
    fun startMainActivity()
    fun saveUserInfo(type: Int)
}