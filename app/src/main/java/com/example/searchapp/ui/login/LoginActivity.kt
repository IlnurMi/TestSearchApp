package com.example.searchapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchapp.R
import com.example.searchapp.interfaces.login.LoginView
import com.example.searchapp.presenters.login.LoginPresenter
import com.example.searchapp.ui.main.MainActivity
import com.example.searchapp.utils.ConstantUtils
import com.example.searchapp.utils.NavigationUtil
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ilnur.data.preferences.PreferenceRepository
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var callbackManager: CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
    private lateinit var preferenceRepository: PreferenceRepository
    private val RC_SIGN_IN: Int = 0
    private var RC_SIGN_F: Int? = 0
    private var firstName: String? = null
    private var lastName: String? = null
    private var photoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initVars()
        setListeners()
    }

    override fun initVars() {
        preferenceRepository = PreferenceRepository.INSTANCE
        loginPresenter = LoginPresenter()
        loginPresenter.setView(this)

        // init google
        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // init facebook
        callbackManager = CallbackManager.Factory.create()
        RC_SIGN_F = facebook_btn_login.requestCode
        facebook_btn_login.setReadPermissions(listOf("email", "public_profile"))
    }

    override fun setListeners() {
        google_btn_login.setOnClickListener { signIn() }

        facebook_btn_login.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {}

                override fun onCancel() {}

                override fun onError(error: FacebookException?) {}
            })
    }

    override fun checkAuthorization() {
        if (preferenceRepository.getAuthorization())
            loginPresenter.startActivity()
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                super.onActivityResult(requestCode, resultCode, data)
                handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
            }

            RC_SIGN_F -> {
                callbackManager.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            firstName = account?.givenName.toString()
            lastName = account?.familyName.toString()
            photoUrl = account?.photoUrl.toString()
            loginPresenter.saveUser(ConstantUtils.GOOGLE)
        } catch (e: ApiException) {
            // TODO handle error
        }
    }

    val tokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {
            if (currentAccessToken != null)
                loadUserProfile(currentAccessToken)
        }
    }

    private fun loadUserProfile(newAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            newAccessToken
        ) { `object`, _ ->
            firstName = `object`?.getString("first_name").toString()
            lastName = `object`?.getString("last_name").toString()
            photoUrl =
                "https://graph.facebook.com/${`object`?.getString("id")}/picture?width=250&height=250"
            loginPresenter.saveUser(ConstantUtils.FACEBOOK)
        }

        val parameters = Bundle()
        parameters.putString("fields", "first_name, last_name, email, id")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun saveUserInfo(type: Int) {
        preferenceRepository.saveAccountType(type)
        photoUrl?.let { preferenceRepository.saveUserInfo(it, "$firstName $lastName") }
        preferenceRepository.setAuthorization(true)
        loginPresenter.startActivity()
    }

    override fun startMainActivity() {
        NavigationUtil.instance.invokeActivity(
            this@LoginActivity,
            MainActivity::class.java,
            true
        )
    }

    override fun onStop() {
        super.onStop()
    }
}