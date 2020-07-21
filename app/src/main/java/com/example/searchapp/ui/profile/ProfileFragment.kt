package com.example.searchapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.searchapp.R
import com.example.searchapp.interfaces.profile.ProfileView
import com.example.searchapp.presenters.profile.ProfilePresenter
import com.example.searchapp.ui.login.LoginActivity
import com.example.searchapp.utils.ConstantUtils
import com.example.searchapp.utils.NavigationUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ilnur.data.preferences.PreferenceRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment: Fragment(), ProfileView {
    private lateinit var preferenceRepository: PreferenceRepository
    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    private object HOLDER{
        val INSTANCE = ProfileFragment()
    }

    companion object{
        val INSTANCE: ProfileFragment by lazy { HOLDER.INSTANCE }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        setListeners()
    }

    override fun initVars() {
        preferenceRepository = PreferenceRepository.INSTANCE
        profilePresenter = ProfilePresenter()
        profilePresenter.setView(this)
        profilePresenter.getUserInfo()

        // init google
        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun setListeners() {
        tv_logout.setOnClickListener {
            profilePresenter.logout()
        }
    }

    override fun logout() {
        when(preferenceRepository.getAccountType()){
            ConstantUtils.FACEBOOK -> {
                profilePresenter.baseLogout()
            }
            ConstantUtils.GOOGLE -> {
                profilePresenter.googleLogout()
            }
        }
    }

    override fun setUserInfo() {
        tv_name.text = preferenceRepository.getUserName()
        if (preferenceRepository.getUserPhoto().isNotEmpty())
            Picasso.get().load(preferenceRepository.getUserPhoto()).into(iv_avatar)
    }

    override fun googleLogout() {
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()
        ) {
            profilePresenter.baseLogout()
        }
    }

    override fun baseLogout() {
        preferenceRepository.setAuthorization(false)
        NavigationUtil.instance.invokeActivity(requireActivity(), LoginActivity::class.java, true)
    }


}

