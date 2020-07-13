package com.example.searchapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.searchapp.R

class ProfileFragment: Fragment() {

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
}