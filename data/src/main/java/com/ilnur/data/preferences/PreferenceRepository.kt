package com.ilnur.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.ilnur.data.network.utils.ConstantUtils

class PreferenceRepository {
    private lateinit var preferences: SharedPreferences

    private object HOLDER {
        val INSTANCE = PreferenceRepository()
    }

    companion object {
        val INSTANCE: PreferenceRepository by lazy { HOLDER.INSTANCE }
    }

    fun init(context: Context) {
        preferences = context.getSharedPreferences(ConstantUtils.PREFS_NAME_APP, Context.MODE_PRIVATE)
    }

    fun setAuthorization(isAuth: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(ConstantUtils.PREF_IS_AUTH, isAuth)
        editor.apply()
    }

    fun getAuthorization(): Boolean = preferences.getBoolean(ConstantUtils.PREF_IS_AUTH, false)

    fun saveUserInfo(photo: String, name: String){
        val editor = preferences.edit()
        editor.putString(ConstantUtils.KEY_PARAM_PHOTO, photo)
        editor.putString(ConstantUtils.KEY_PARAM_NAME, name)
        editor.apply()
    }

    fun saveAccountType(type: Int){
        val editor = preferences.edit()
        editor.putInt(ConstantUtils.AC_TYPE, type)
        editor.apply()
    }

    fun getAccountType(): Int = preferences.getInt(ConstantUtils.AC_TYPE, 0)

    fun getUserPhoto(): String{
        return preferences.getString(ConstantUtils.KEY_PARAM_PHOTO, "")
    }

    fun getUserName(): String{
        return preferences.getString(ConstantUtils.KEY_PARAM_NAME, "")
    }
}