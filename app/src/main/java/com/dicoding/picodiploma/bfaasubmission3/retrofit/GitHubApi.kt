package com.dicoding.picodiploma.bfaasubmission3.retrofit

import com.dicoding.picodiploma.bfaasubmission3.BuildConfig
import com.dicoding.picodiploma.bfaasubmission3.model.SearchResponse
import com.dicoding.picodiploma.bfaasubmission3.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    companion object {
        private const val API_TOKEN = BuildConfig.API_TOKEN
    }
    @GET("users")
    @Headers("Authorization: token $API_TOKEN", "User-Agent: request")
    fun getUsers(): Call<ArrayList<User>>

    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN", "User-Agent: request")
    fun getSearchUser(@Query("q")Username : String): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN", "User-Agent: request")
    fun getDetail(@Path("username") Username: String): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN", "User-Agent: request")
    fun followers(@Path("username")Username: String):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN", "User-Agent: request")
    fun following(@Path("username")Username: String):Call<ArrayList<User>>
}