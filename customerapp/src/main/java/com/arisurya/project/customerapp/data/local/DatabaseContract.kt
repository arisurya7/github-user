package com.arisurya.project.customerapp.data.local

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.arisurya.project.githubuser3"
    const val SCHEMA = "content"

    internal class FavoriteUsersColumns : BaseColumns {
        companion object {
            private const val TABLE_NAME = "tb_favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"
            @Suppress("HasPlatformType")
            val CONTENT_URI =
                Uri.Builder().scheme(SCHEMA).authority(AUTHORITY).appendPath(TABLE_NAME).build()
        }
    }
}