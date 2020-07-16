package com.example.searchapp.models

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapp.R
import com.ilnur.domain.models.response.UserModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var itemAvatar = itemView.findViewById<CircleImageView>(R.id.item_avatar)
    private var itemName = itemView.findViewById<TextView>(R.id.item_name)

    fun bind(data: UserModel) {
        Picasso
            .get()
            .load(data.avatar)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_error)
            .into(itemAvatar)
        itemName.text = data.name
    }
}