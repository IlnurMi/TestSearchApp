package com.example.searchapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapp.R
import com.example.searchapp.models.UsersViewHolder
import com.ilnur.domain.models.response.UserModel

class UsersAdapter(private var users: MutableList<UserModel>):RecyclerView.Adapter<UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.users_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addItem(list: List<UserModel>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }

    fun addItemsToEnd(list: List<UserModel>){
        users.addAll(list)
        notifyDataSetChanged()
    }

    fun cleanList(){
        users.clear()
        notifyDataSetChanged()
    }

}