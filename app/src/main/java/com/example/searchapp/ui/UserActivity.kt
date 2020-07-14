package com.example.searchapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.searchapp.R
import com.example.searchapp.ui.login.VKUser
import com.example.searchapp.ui.login.VKUsersRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class UserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        requestUser()
    }

    private fun requestUser(){
        VK.execute(VKUsersRequest(), object: VKApiCallback<List<VKUser>> {
            override fun fail(error: Exception) {
                //TODO("Not yet implemented")
            }

            override fun success(result: List<VKUser>) {
                //TODO("Not yet implemented")
                Log.d("TAG", "Success")
            }

        })
    }
}