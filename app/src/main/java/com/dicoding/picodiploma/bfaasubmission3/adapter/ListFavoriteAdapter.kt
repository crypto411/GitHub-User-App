package com.dicoding.picodiploma.bfaasubmission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.bfaasubmission3.R
import com.dicoding.picodiploma.bfaasubmission3.activities.DetailActivity
import com.dicoding.picodiploma.bfaasubmission3.util.ExtensionFun.load
import com.dicoding.picodiploma.bfaasubmission3.model.User

class ListFavoriteAdapter(private val listUser: List<User>): RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {
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
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("user", user)
            holder.itemView.context.startActivity(intent)
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