package com.ilnur.domain.interactors.search

import android.util.Log
import com.ilnur.domain.interfaces.repositories.search.SearchRepositoryInterface
import com.ilnur.domain.listeners.search.SearchPresenterListener
import com.ilnur.domain.listeners.search.SearchRepositoryListener
import com.ilnur.domain.models.response.UserModel

class SearchInteractor(private val searchRepository: SearchRepositoryInterface) : SearchRepositoryListener {
    private lateinit var searchPresenterListener: SearchPresenterListener
    init {
        searchRepository.setListener(this)
    }

    fun setPresenter(listener: SearchPresenterListener){
        searchPresenterListener = listener
    }

    fun getUsers(name: String, page: Int){
        searchRepository.getUsers(name, page)
    }

    override fun sendUsers(users: List<UserModel>) {
        searchPresenterListener.sendUsers(users)
    }
}