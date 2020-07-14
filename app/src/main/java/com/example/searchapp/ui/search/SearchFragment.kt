package com.example.searchapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchapp.R
import com.example.searchapp.interfaces.search.SearchView
import com.example.searchapp.presenters.search.SearchPresenter
import com.ilnur.data.network.api.ApiClient
import com.ilnur.data.repositories.search.SearchRepository
import com.ilnur.domain.interactors.search.SearchInteractor
import com.ilnur.domain.models.response.UserModel
import kotlinx.android.synthetic.main.search_fragment.*
import java.util.ArrayList

class SearchFragment: Fragment(), SearchView{
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var usersList: MutableList<UserModel>

    private object HOLDER{
        val INSTANCE = SearchFragment()
    }

    companion object{
        val INSTANCE: SearchFragment by lazy { HOLDER.INSTANCE }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
        setListeners()
    }

    override fun initVars() {
        searchPresenter = SearchPresenter(SearchInteractor(SearchRepository(ApiClient.getRestClient(requireContext()))))
        searchPresenter.setView(this)

        usersList = ArrayList()
        usersAdapter = UsersAdapter(usersList)
        rv_search.layoutManager = LinearLayoutManager(requireContext())
        rv_search.adapter = usersAdapter
    }

    override fun setListeners() {
        search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchPresenter.searchUsers(newText.toString())
                return true
            }
        })
    }

    override fun populateUsersAdapter(users: List<UserModel>) {
        usersAdapter.addItem(users)
    }
}