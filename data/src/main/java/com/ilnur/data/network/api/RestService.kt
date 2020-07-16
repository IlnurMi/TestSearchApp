package com.ilnur.data.network.api

import com.ilnur.data.network.models.MainResponseArray
import com.ilnur.data.network.models.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {
    // github user search region
    @GET("/search/users?")
    fun searchUsers(@Query("q")name: String,
                    @Query("page")page: Int,
                    @Query("per_page")pageCount: Int): Flowable<MainResponseArray<User>>
    // end region
}