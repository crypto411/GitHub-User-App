package com.dicoding.picodiploma.consumergithubuser
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
        val id: Int,
        val login: String?,
        val avatar_url: String?
): Parcelable
