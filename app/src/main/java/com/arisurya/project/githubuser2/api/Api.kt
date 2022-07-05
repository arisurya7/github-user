package com.arisurya.project.githubuser2.api

import com.arisurya.project.githubuser2.data.model.DetailUserResponse
import com.arisurya.project.githubuser2.data.model.User
import com.arisurya.project.githubuser2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token d544c678bc762e5aff899702ce8a1964962613d7")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token d544c678bc762e5aff899702ce8a1964962613d7")
    fun getDetailUser(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token d544c678bc762e5aff899702ce8a1964962613d7")
    fun getFollowers(
        @Path("username") username : String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token d544c678bc762e5aff899702ce8a1964962613d7")
    fun getFollowing(
        @Path("username") username : String
    ): Call<ArrayList<User>>
}