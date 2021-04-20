package com.dicoding.picodiploma.bfaasubmission3.model
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "login") val login: String?,
        @ColumnInfo(name = "avatar_url") val avatar_url: String?,
        @ColumnInfo(name = "name") var name: String?,
        @ColumnInfo(name = "company") var company: String?,
        @ColumnInfo(name = "location") var location: String?,
        @ColumnInfo(name = "followers") var followers: Int,
        @ColumnInfo(name = "following") var following: Int,
        @ColumnInfo(name = "followers_url") var followers_url: String?,
        @ColumnInfo(name = "following_url") var following_url: String?,
        @ColumnInfo(name = "favorite") var favorite: Boolean = false

): Parcelable
