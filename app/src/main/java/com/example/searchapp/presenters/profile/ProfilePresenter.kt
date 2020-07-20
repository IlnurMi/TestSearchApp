package com.example.searchapp.presenters.profile

import com.example.searchapp.interfaces.profile.ProfileView

class ProfilePresenter {
    private var view: ProfileView? = null

    fun setView(view: ProfileView){
        this.view = view
    }

    fun logout(){
        view?.logout()
    }

    fun baseLogout(){
        view?.baseLogout()
    }

    fun googleLogout(){
        view?.googleLogout()
    }


    fun getUserInfo(){
        view?.setUserInfo()
    }
}