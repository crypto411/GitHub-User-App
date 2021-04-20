package com.dicoding.picodiploma.bfaasubmission3.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.bfaasubmission3.retrofit.DataRepository
import com.dicoding.picodiploma.bfaasubmission3.adapter.ListUserAdapter
import com.dicoding.picodiploma.bfaasubmission3.R
import com.dicoding.picodiploma.bfaasubmission3.model.SearchResponse
import com.dicoding.picodiploma.bfaasubmission3.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvData: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var curUsers: ArrayList<User>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "GitHub User"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_fab_github_white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvData = findViewById(R.id.rv_data)
        progressBar = findViewById(R.id.fol_progressbar)

        showProgressBar(true)

        val client = DataRepository.client
        client.getUsers().enqueue(object: Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if(response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        showUserList(data)
                        curUsers = data

                        showProgressBar(false)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                showProgressBar(false)
            }

        })
    }

    private fun showUserList(users: ArrayList<User>) {
        if(users.isNotEmpty()) {
            rvData.layoutManager = LinearLayoutManager(this)
            rvData.adapter = ListUserAdapter(users)
        }
    }
    private fun hideUserList() {
        rvData.layoutManager = null
        rvData.adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val actionSearchView = menu.findItem(R.id.search)
        val favoriteMenu = menu.findItem(R.id.favorite)
        val settingMenu = menu.findItem(R.id.settings)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        actionSearchView.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                hideUserList()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                curUsers?.let { showUserList(it) }
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                showProgressBar(true)
                val client = DataRepository.client
                client.getSearchUser(query).enqueue(object: Callback<SearchResponse> {
                    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                        if(response.isSuccessful) {
                            val data = response.body()
                            if (data != null) {
                                if (data.items != null) {
                                    showUserList(data.items)
                                    showProgressBar(false)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        showProgressBar(false)
                    }

                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        favoriteMenu.setOnMenuItemClickListener {
            val intent = Intent(baseContext, FavoriteActivity::class.java)
            startActivity(intent)
            true
        }
        settingMenu.setOnMenuItemClickListener {
            val intent = Intent(baseContext, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun showProgressBar(state: Boolean) {
        if(state)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.INVISIBLE
    }
}
