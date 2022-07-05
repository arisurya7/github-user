package com.arisurya.project.githubuser3.data.model

data class DetailUserResponse(
    val id : Int,
    val login : String,
    val name : String,
    val avatar_url : String,
    val company : String,
    val location : String,
    val public_repos : Int,
    val followers :  Int,
    val following : Int,
    val followers_url : String,
    val following_url : String
)
