package com.example.searchapp.interfaces.search

import com.ilnur.domain.models.response.UserModel

interface SearchView {
    fun initVars()
    fun setListeners()
    fun populateUsersAdapter(users: List<UserModel>)
    fun showProgressbar(show: Boolean)
    fun clearSearchView()
    fun showToast(message: String)
}