package com.example.searchapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.searchapp.R
import com.example.searchapp.interfaces.main.MainView
import com.example.searchapp.presenters.main.MainPresenter
import com.example.searchapp.utils.ConstantUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVars()
        setListeners()
    }

    override fun initVars() {
        mainPresenter = MainPresenter()
        mainPresenter.setView(this)
    }

    override fun setListeners() {
        navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_profile -> {
                    mainPresenter.navigationItemClick(ConstantUtils.PROFILE_FRAGMENT)
                }
                R.id.item_search -> {
                    mainPresenter.navigationItemClick(ConstantUtils.SEARCH_FRAGMENT)
                }
            }
            true
        }
    }
g
    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .commit()
    }

    override fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment, fragment)
            .commit()
    }
}
