package com.dicoding.picodiploma.bfaasubmission3.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.picodiploma.bfaasubmission3.retrofit.DataRepository
import com.dicoding.picodiploma.bfaasubmission3.R
import com.dicoding.picodiploma.bfaasubmission3.fragmentviewpager.SectionPagerAdapter
import com.dicoding.picodiploma.bfaasubmission3.util.ExtensionFun.load
import com.dicoding.picodiploma.bfaasubmission3.model.User
import com.dicoding.picodiploma.bfaasubmission3.room.AppDatabase
import com.dicoding.picodiploma.bfaasubmission3.util.ExtensionFun.showToast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var imgAvatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvName: TextView
    private lateinit var tvCompany: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvRepository: TextView
    private lateinit var tvFollowerfollowing: TextView
    private lateinit var floatingFav: FloatingActionButton
    private var db: AppDatabase? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_follower,
                R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        title = "Detail"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        db = AppDatabase.getInstance(this)

        val userExtra = intent.getParcelableExtra<User>("user")
        if(userExtra != null) {
            imgAvatar = findViewById(R.id.civ_user_detail)
            floatingFav = findViewById(R.id.floating_fav)
            tvUsername = findViewById(R.id.tv_user)
            tvName = findViewById(R.id.tv_user_name)
            tvCompany = findViewById(R.id.tv_user_company)
            tvLocation = findViewById(R.id.tv_user_location)
            tvRepository = findViewById(R.id.tv_user_repos)
            tvFollowerfollowing = findViewById(R.id.tv_followerfollowing)

            var user = db?.favoriteDao()?.findById(userExtra.id)
            if(user?.login != null) {
                loadViewWithData(user)
            }
            else {
                val client = DataRepository.client
                userExtra.login?.let {
                    client.getDetail(it).enqueue(object : Callback<User> {
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                user = response.body()!!
                                if (user?.login != null) {
                                    loadViewWithData(user)
                                }
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            applicationContext.showToast(t.message)
                        }

                    })
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun FloatingActionButton.setFavoriteState(state: Boolean) {
        if(state)
            this.setImageResource(R.drawable.ic_baseline_favorite_24)
        else
            this.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }

    @SuppressLint("SetTextI18n")
    private fun loadViewWithData(user: User?) {
        imgAvatar.load(user?.avatar_url)
        floatingFav.setFavoriteState(user!!.favorite)
        floatingFav.setOnClickListener {

            user.favorite = !user.favorite
            user.favoriteState(user.favorite)
            if(user.favorite)
                applicationContext.showToast("Added to favorite!")
            else
                applicationContext.showToast("Removed from favorite")
        }
        tvName.text = user.name
        tvUsername.text = user.login
        tvCompany.text = user.company ?: "-"
        tvLocation.text = user.location ?: "-"
        tvRepository.text = "https://github.com/${user.login}?tab=repositories"
        if (!tvRepository.text.equals("-")) {
            tvRepository.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(tvRepository.text.toString())
                startActivity(intent)
            }
        }
        tvFollowerfollowing.text =
                StringBuilder("Follower ${user.followers} - Following ${user.following}")

        val sectionsPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        sectionsPagerAdapter.model = user
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }
    private fun User.favoriteState(state: Boolean) {
        db?.favoriteDao()?.insert(this)
        floatingFav.setFavoriteState(state)
    }
}