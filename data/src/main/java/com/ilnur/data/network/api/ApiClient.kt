package com.ilnur.data.network.api

import android.content.Context
import com.example.data.BuildConfig
import com.ilnur.data.network.interceptors.ConnectivityInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient() {

    // return rest client
    fun getRestClient(context: Context): RestService {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(context))
                .build()
        return retrofit.create(RestService::class.java)
    }

    // return okHttp client
    private fun getOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(ConnectivityInterceptor(context))
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build()
    }
}