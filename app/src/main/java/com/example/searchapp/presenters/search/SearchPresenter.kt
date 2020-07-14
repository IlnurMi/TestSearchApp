package com.example.searchapp.presenters.search

import com.example.searchapp.interfaces.search.SearchView
import com.ilnur.domain.interactors.search.SearchInteractor
import com.ilnur.domain.listeners.search.SearchPresenterListener
import com.ilnur.domain.models.response.UserModel

class SearchPresenter(private val interactor: SearchInteractor): SearchPresenterListener {
    private lateinit var searchView: SearchView
    private var page: Int = 1
    private var name: String = ""

    fun setView(view: SearchView){
        searchView = view
        searchView.showProgressbar(false)
        interactor.setPresenter(this)
    }

    fun searchUsers(name: String){
        page = 1
        this.name = name
        searchView.showProgressbar(true)
        interactor.getUsers(name, page)
    }

    override fun sendUsers(users: List<UserModel>) {
        searchView.showProgressbar(false)
        searchView.populateUsersAdapter(users)
    }

    override fun sendError(error: String) {
        searchView.showProgressbar(false)
        searchView.showToast(error)
    }

    fun loadUsers(){
        page++
        interactor.getUsers(name, page)
    }

    fun cancel(){
        interactor.cancelRequest()
        searchView.showProgressbar(false)
        searchView.clearSearchView()
    }
}