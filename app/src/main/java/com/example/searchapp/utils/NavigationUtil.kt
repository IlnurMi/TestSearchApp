package com.example.searchapp.utils

import android.app.Activity
import android.content.Intent

class NavigationUtil {

    private object HOLDER{
        val INSTANCE = NavigationUtil()
    }

    companion object{
        val instance: NavigationUtil by lazy { HOLDER.INSTANCE }
    }

    fun<T> invokeActivity(activity: Activity, tClass: Class<T>, finish: Boolean){
        val intent = Intent(activity, tClass)
        activity.startActivity(intent)
        if (finish)
            activity.finish()
    }
}