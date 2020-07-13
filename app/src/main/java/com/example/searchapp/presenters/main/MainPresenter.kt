package com.example.searchapp.presenters.main

import com.example.searchapp.interfaces.main.MainView
import com.example.searchapp.ui.profile.ProfileFragment
import com.example.searchapp.ui.search.SearchFragment
import com.example.searchapp.utils.ConstantUtils

class MainPresenter {
    private lateinit var view: MainView

    fun setView(view: MainView){
        this.view = view
        this.view.showSearchView(false)
        this.view.addFragment(ProfileFragment.INSTANCE)
    }

    fun navigationItemClick(item: Int){
        when(item){
            ConstantUtils.PROFILE_FRAGMENT -> view.replaceFragment(ProfileFragment.INSTANCE)
            ConstantUtils.SEARCH_FRAGMENT -> view.replaceFragment(SearchFragment.INSTANCE)
        }
    }
}