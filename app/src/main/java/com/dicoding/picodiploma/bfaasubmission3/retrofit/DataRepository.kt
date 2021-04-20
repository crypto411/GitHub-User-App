package com.dicoding.picodiploma.bfaasubmission3.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRepository {
    private const val Base_url = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(Base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val client = retrofit.create(GitHubApi::class.java)!!
}