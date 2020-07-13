package com.example.searchapp.interfaces.main

import androidx.fragment.app.Fragment

interface MainView {
    fun initVars()
    fun setListeners()
    fun replaceFragment(fragment: Fragment)
    fun addFragment(fragment: Fragment)
    fun showSearchView(show: Boolean)
}