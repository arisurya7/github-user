package com.arisurya.project.githubuser3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arisurya.project.githubuser3.data.local.DatabaseUser
import com.arisurya.project.githubuser3.data.local.FavoriteUser
import com.arisurya.project.githubuser3.data.local.FavoriteUserDao

@Suppress("JoinDeclarationAndAssignment")
class FavoriteViewModel(application: Application):AndroidViewModel(application) {

    private var userDao : FavoriteUserDao?
    private var userDb : DatabaseUser?
    init {
        userDb = DatabaseUser.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser():LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}