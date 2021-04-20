package com.dicoding.picodiploma.consumergithubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.consumergithubuser.util.ExtensionFun.load
import com.dicoding.picodiploma.consumergithubuser.util.ExtensionFun.showToast

class ListConsumerAdapter(private val listUser: ArrayList<User>): RecyclerView.Adapter<ListConsumerAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.row_item_fav, viewGroup, false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.imgAvatar.load(user.avatar_url)
        holder.tvName.text = user.login
        holder.itemView.setOnClickListener {
            holder.itemView.context.showToast(user.login.toString())
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.civ_list_user_fav)
        var tvName: TextView = itemView.findViewById(R.id.tv_list_username_fav)
    }
}