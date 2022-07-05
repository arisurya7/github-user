package com.arisurya.project.githubuser3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login : String,
    val id : Int,
    val avatar_url : String,
):Parcelable
