package com.example.searchapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapp.R
import com.example.searchapp.interfaces.search.SearchView
import com.example.searchapp.presenters.search.SearchPresenter
import com.ilnur.data.network.api.ApiClient
import com.ilnur.data.repositories.search.SearchRepository
import com.ilnur.domain.interactors.search.SearchInteractor
import com.ilnur.domain.models.response.UserModel
import kotlinx.android.synthetic.main.search_fragment.*
import java.util.ArrayList
import kotlin.math.log

class SearchFragment : Fragment(), SearchView {
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var usersList: MutableList<UserModel>
    private lateinit var linearLayoutManager: LinearLayoutManager

    private object HOLDER {
        val INSTANCE = SearchFragment()
    }

    companion object {
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
        searchPresenter = SearchPresenter(
            SearchInteractor(
                SearchRepository(
                    ApiClient().getRestClient(requireContext())
                )
            )
        )
        searchPresenter.setView(this)
        usersList = ArrayList()
        usersAdapter = UsersAdapter(usersList)
        linearLayoutManager = LinearLayoutManager(requireContext())
        rv_search.layoutManager = linearLayoutManager
        rv_search.adapter = usersAdapter
    }

    override fun setListeners() {
        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty())
                    searchPresenter.searchUsers(newText.toString())
                    usersAdapter.cleanList()
                return true
            }
        })

        rv_search.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount: Int = rv_search.layoutManager?.childCount!!.toInt()
                    val allItemCount: Int = rv_search.layoutManager?.itemCount!!.toInt()
                    val firstVisibleItemPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + firstVisibleItemPosition >= allItemCount){
                        searchPresenter.loadUsers()
                    }
                }
            }
        })
    }

    override fun populateUsersAdapter(users: List<UserModel>) {
        if (usersAdapter.itemCount > 0)
            usersAdapter.addItemsToEnd(users)
        else
            usersAdapter.addItem(users)
    }

    override fun showProgressbar(show: Boolean) {
        if (isAdded && isVisible) {
            if (show)
                progress_bar.visibility = View.VISIBLE
            else
                progress_bar.visibility = View.GONE
        }
    }

    override fun clearSearchView() {
        search.isIconified = true
    }

    override fun showToast(message: String) {
        if (isAdded && isVisible)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        searchPresenter.cancel()
    }
}