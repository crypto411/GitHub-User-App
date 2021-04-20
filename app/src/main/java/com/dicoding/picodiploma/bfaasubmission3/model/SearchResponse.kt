package com.dicoding.picodiploma.bfaasubmission3.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_result")
    val incompleteResult: Boolean,

    val items: ArrayList<User>?
): Parcelable
