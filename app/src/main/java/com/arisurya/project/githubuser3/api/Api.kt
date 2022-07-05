package com.arisurya.project.githubuser3.api

import com.arisurya.project.githubuser3.BuildConfig
import com.arisurya.project.githubuser3.data.model.DetailUserResponse
import com.arisurya.project.githubuser3.data.model.User
import com.arisurya.project.githubuser3.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.ApiKey}")
    fun getDetailUser(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.ApiKey}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.ApiKey}")
    fun getFollowing(
        @Path("username") username : String
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.ApiKey}")
    fun getFollowers(
        @Path("username") username : String
    ): Call<ArrayList<User>>
}