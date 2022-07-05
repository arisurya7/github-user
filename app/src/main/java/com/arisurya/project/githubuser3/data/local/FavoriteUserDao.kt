package com.arisurya.project.githubuser3.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUser:FavoriteUser)

    @Query("SELECT * FROM tb_favorite_user")
    fun getFavoriteUser():LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM tb_favorite_user WHERE tb_favorite_user.id=:id")
    suspend fun checkUser(id:Int):Int

    @Query("SELECT * FROM tb_favorite_user")
    fun findAll(): Cursor

    @Query("DELETE FROM tb_favorite_user WHERE tb_favorite_user.id=:id")
    suspend fun removeFromFavorite(id:Int):Int
}