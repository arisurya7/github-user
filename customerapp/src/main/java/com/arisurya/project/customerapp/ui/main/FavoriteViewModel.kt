package com.arisurya.project.customerapp.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arisurya.project.customerapp.data.local.DatabaseContract
import com.arisurya.project.customerapp.data.model.User

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var listUser = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUsersColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        listUser.postValue(MapHelper.cursorToArrayList(cursor, context))
    }

    fun getFavoriteUser(): LiveData<ArrayList<User>> = listUser
}