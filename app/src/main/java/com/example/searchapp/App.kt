package com.example.searchapp

import android.app.Application
import com.ilnur.data.preferences.PreferenceRepository

class App: Application() {
    var preferenceRepository: PreferenceRepository? = null

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        preferenceRepository = PreferenceRepository.INSTANCE
        preferenceRepository!!.init(applicationContext)
    }
}