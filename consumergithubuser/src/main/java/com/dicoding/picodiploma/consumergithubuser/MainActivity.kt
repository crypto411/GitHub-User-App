package com.dicoding.picodiploma.consumergithubuser

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvData: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_fab_github_white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvData = findViewById(R.id.rvData)
        rvData.layoutManager = LinearLayoutManager(this)

        fetchDataFromContentProvider().observe(this, {
            rvData.adapter = ListConsumerAdapter(it)
        })
    }

    private fun fetchDataFromContentProvider(): LiveData<ArrayList<User>> {
        val users = ArrayList<User>()
        val mutableUsers = MutableLiveData<ArrayList<User>>()
        val uri = Uri.Builder()
            .scheme("content")
            .authority("com.dicoding.picodiploma.bfaasubmission3.provider")
            .appendPath("user")
            .build()

        Log.d("Uri", uri.toString())
        val cursor = contentResolver.query(uri, null, null, null, null, null)
        if(cursor != null) {
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow("avatar_url"))
                val user = User(id, login, avatarUrl)
                users.add(user)
            }
        }
        mutableUsers.postValue(users)
        return mutableUsers
    }
}