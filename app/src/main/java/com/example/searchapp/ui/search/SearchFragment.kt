package com.example.searchapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.searchapp.R
import com.example.searchapp.interfaces.search.SearchView
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment: Fragment(), SearchView{

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
        setListeners()
    }

    override fun initVars() {
        // TODO init
    }

    override fun setListeners() {
        search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("TAG", "onQueryTextSubmit: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("TAG", "onQueryTextChange: $newText")
                return true
            }
        })
    }
}