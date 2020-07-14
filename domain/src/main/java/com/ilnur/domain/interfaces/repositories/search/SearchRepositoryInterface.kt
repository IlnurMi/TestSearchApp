package com.ilnur.domain.interfaces.repositories.search

import com.ilnur.domain.listeners.search.SearchRepositoryListener

interface SearchRepositoryInterface {
    fun setListener(listener: SearchRepositoryListener)
    fun getUsers(name: String, page: Int)
    fun cancelRequest()
}