package com.dicoding.picodiploma.bfaasubmission3.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.bfaasubmission3.model.User

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM user where favorite = 1")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :login LIMIT 1")
    fun findById(login: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: User)

    // untuk content provider
    @Query("SELECT * FROM user")
    fun fetchAll(): Cursor
}