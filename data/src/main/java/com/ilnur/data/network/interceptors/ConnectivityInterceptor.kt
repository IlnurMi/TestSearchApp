package com.ilnur.data.network.interceptors

import android.content.Context
import com.example.data.R
import com.ilnur.data.network.exceptions.NoConnectivityException
import com.ilnur.data.network.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil().isOnline(context)){
            throw NoConnectivityException(context.getString(R.string.no_connection_network))
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}