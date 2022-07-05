package com.arisurya.project.githubuser3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract  class DatabaseUser : RoomDatabase() {
    companion object{
        private var INSTANCE : DatabaseUser?=null
        fun getDatabase(context: Context?): DatabaseUser? {
            if(INSTANCE==null){
                synchronized(DatabaseUser::class){
                    if (context != null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, DatabaseUser::class.java, "db_user").build()
                    }
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteUserDao():FavoriteUserDao
}