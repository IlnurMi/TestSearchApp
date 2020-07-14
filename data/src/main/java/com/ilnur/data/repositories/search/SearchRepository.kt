package com.ilnur.data.repositories.search

import com.ilnur.data.network.api.RestService
import com.ilnur.data.network.models.MainResponseArray
import com.ilnur.data.network.models.User
import com.ilnur.data.network.utils.ConstantUtils
import com.ilnur.domain.interfaces.repositories.search.SearchRepositoryInterface
import com.ilnur.domain.listeners.search.SearchRepositoryListener
import com.ilnur.domain.models.response.UserModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class SearchRepository(private val restService: RestService) : SearchRepositoryInterface {
    private lateinit var userDisposable: Disposable
    private lateinit var searchRepositoryListener: SearchRepositoryListener

    override fun setListener(listener: SearchRepositoryListener) {
        searchRepositoryListener = listener
    }

    override fun getUsers(name: String, page: Int) {
        userDisposable = restService
            .searchUsers(name, page, ConstantUtils.PAGE_COUNT)
            .delaySubscription(600, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                userResponseSuccess(result)
            }, { error ->
                userResponseError(error)
            })
    }

    private fun userResponseSuccess(users: MainResponseArray<User>) {
        searchRepositoryListener.sendUsers(convertResponseUsers(users.items))
    }

    private fun userResponseError(error: Throwable) {
        // TODO handle error
    }

    private fun convertResponseUsers(users: List<User>): List<UserModel> {
        val userList: MutableList<UserModel> = ArrayList()
        for (user in users) {
            userList.add(setUsersModel(user))
        }
        return userList
    }

    private fun setUsersModel(user: User): UserModel {
        return UserModel(
            user.avatarUrl,
            user.login
        )
    }
}


