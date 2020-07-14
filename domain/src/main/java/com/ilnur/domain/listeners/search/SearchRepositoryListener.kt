package com.ilnur.domain.listeners.search

import com.ilnur.domain.models.response.UserModel

interface SearchRepositoryListener {
    fun sendUsers(users: List<UserModel>)
}