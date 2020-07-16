package com.example.searchapp.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.searchapp.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 0;
    private val RC_SIGN_f: Int = 1;
    private val RC_SIGN_If: Int = 2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()

        facebook_btn_login.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                @SuppressLint("SetTextI18n")
                override fun onSuccess(result: LoginResult?) {
                    tv_id.text = "UserId: ${result?.accessToken?.userId}"
                    val url: String =
                        "https://graph.facebook.com/${result?.accessToken?.userId}/picture?return_ssl_resources=1"
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

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        callbackManager = CallbackManager.Factory.create()

        facebook_btn_login.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    tv_id.text = "UserId: ${result?.accessToken?.userId}"
                    val url: String =
                        "https://graph.facebook.com/${result?.accessToken?.userId}/picture?return_ssl_resources=1"
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

        vk_btn_login.setOnClickListener {
            VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }

        google_btn_login.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        when (requestCode) {
            RC_SIGN_IN -> {
                super.onActivityResult(requestCode, resultCode, data)
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }

            RC_SIGN_If -> {
                super.onActivityResult(requestCode, resultCode, data)
                callbackManager.onActivityResult(requestCode, resultCode, data)
            }

            RC_SIGN_f -> {
                val callback = object : VKAuthCallback {
                    override fun onLogin(token: VKAccessToken) {
                        Log.d("TAG", "onLogin: Success")
                    }

                    override fun onLoginFailed(errorCode: Int) {
                        Log.d("TAG", "onLogin: Error")
                    }
                }
                if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(FragmentActivity.TAG, "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val callback = object : VKAuthCallback {
//            override fun onLogin(token: VKAccessToken) {
//                Log.d("TAG", "onLogin: Success")
//            }
//
//            override fun onLoginFailed(errorCode: Int) {
//                Log.d("TAG", "onLogin: Error")
//            }
//        }
//        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    fun startFrom(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
}