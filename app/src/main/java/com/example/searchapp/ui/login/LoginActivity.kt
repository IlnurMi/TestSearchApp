package com.example.searchapp.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.searchapp.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()

        facebook_btn_login.registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
            @SuppressLint("SetTextI18n")
            override fun onSuccess(result: LoginResult?) {
                tv_id.text = "UserId: ${result?.accessToken?.userId}"
                val url: String = "https://graph.facebook.com/${result?.accessToken?.userId}/picture?return_ssl_resources=1"
                Picasso.get().load(url).into(im_id)
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}