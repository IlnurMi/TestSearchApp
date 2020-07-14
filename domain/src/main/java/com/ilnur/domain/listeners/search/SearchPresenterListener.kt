package com.ilnur.domain.listeners.search

import com.ilnur.domain.models.response.UserModel

interface SearchPresenterListener {
    fun sendUsers(users: List<UserModel>)
    fun sendError(error: String)
}