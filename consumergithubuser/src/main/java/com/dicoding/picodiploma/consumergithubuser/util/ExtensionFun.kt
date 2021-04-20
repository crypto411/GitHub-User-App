package com.dicoding.picodiploma.consumergithubuser.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.consumergithubuser.R

object ExtensionFun {
    fun ImageView.load(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(
                    RequestOptions().centerCrop()
                            .placeholder(R.drawable.ic_fab_github)
                            .error(R.drawable.ic_fab_github)
            )
            .into(this)
    }
    fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }
}