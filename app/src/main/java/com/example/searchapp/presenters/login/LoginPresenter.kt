package com.example.searchapp.presenters.login

import com.example.searchapp.interfaces.login.LoginView

class LoginPresenter {
    private var loginView: LoginView? = null

    fun setView(view: LoginView){
        loginView = view
        loginView?.checkAuthorization()
    }

    fun saveUserInfo(type: Int){
        loginView?.saveUserInfo(type)
    }

    fun startActivity(){
        loginView?.startMainActivity()
    }

    fun stop(){
        loginView = null
    }
}