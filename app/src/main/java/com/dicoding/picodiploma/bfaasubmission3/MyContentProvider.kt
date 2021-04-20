package com.dicoding.picodiploma.bfaasubmission3

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.picodiploma.bfaasubmission3.room.AppDatabase
import com.dicoding.picodiploma.bfaasubmission3.room.FavoriteDAO

class MyContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.dicoding.picodiploma.bfaasubmission3.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "user", 1)
        }
        init {
            uriMatcher.addURI(AUTHORITY, "user", 1)
        }
    }

    private lateinit var favoriteDao: FavoriteDAO

    override fun onCreate(): Boolean {
        favoriteDao = AppDatabase.getInstance(context as Context).favoriteDao()
        return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val cursor: Cursor?
        if(uriMatcher.match(uri) == 1) {
            cursor = favoriteDao.fetchAll()
            if(context != null)
                cursor.setNotificationUri(context?.contentResolver, uri)
        }
        else
            cursor = null
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}