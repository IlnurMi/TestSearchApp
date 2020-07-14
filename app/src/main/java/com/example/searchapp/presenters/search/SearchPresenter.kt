package com.example.searchapp.presenters.search

import com.example.searchapp.interfaces.search.SearchView
import com.ilnur.domain.interactors.search.SearchInteractor
import com.ilnur.domain.listeners.search.SearchPresenterListener
import com.ilnur.domain.models.response.UserModel

class SearchPresenter(private val interactor: SearchInteractor): SearchPresenterListener {
    private lateinit var searchView: SearchView

    fun setView(view: SearchView){
        searchView = view
        interactor.setPresenter(this)
    }

    fun searchUsers(name: String){
        interactor.getUsers(name, 0)
    }

    override fun sendUsers(users: List<UserModel>) {
        searchView.populateUsersAdapter(users)
    }
}