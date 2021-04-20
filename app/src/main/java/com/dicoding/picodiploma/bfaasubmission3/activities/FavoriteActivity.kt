package com.dicoding.picodiploma.bfaasubmission3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.bfaasubmission3.adapter.ListFavoriteAdapter
import com.dicoding.picodiploma.bfaasubmission3.R
import com.dicoding.picodiploma.bfaasubmission3.model.User
import com.dicoding.picodiploma.bfaasubmission3.room.AppDatabase

class FavoriteActivity : AppCompatActivity() {

    private lateinit var rvDataFav: RecyclerView
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        title = "Favorites"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        rvDataFav = findViewById(R.id.rv_data_favorite)
        db = AppDatabase.getInstance(this)

        db?.favoriteDao()?.getAll()?.observe(this, {
            showUserList(it)
        })
    }

    private fun showUserList(users: List<User>) {
        if(users.isNotEmpty()) {
            rvDataFav.layoutManager = LinearLayoutManager(this)
            rvDataFav.adapter = ListFavoriteAdapter(users)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}