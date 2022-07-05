package com.arisurya.project.githubuser3.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arisurya.project.githubuser3.api.RetrofitClient
import com.arisurya.project.githubuser3.data.local.DatabaseUser
import com.arisurya.project.githubuser3.data.local.FavoriteUser
import com.arisurya.project.githubuser3.data.local.FavoriteUserDao
import com.arisurya.project.githubuser3.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("JoinDeclarationAndAssignment")
class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteUserDao?
    private var userDb: DatabaseUser?

    init {
        userDb = DatabaseUser.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failed Load Data", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> = user

    fun addUserFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkDetailUser(id: Int) = userDao?.checkUser(id)

    fun removeUserFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}