package com.arisurya.project.customerapp.ui.main

import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.arisurya.project.customerapp.data.local.DatabaseContract
import com.arisurya.project.customerapp.data.model.User

object MapHelper {
    fun cursorToArrayList(cursor: Cursor?, context: Context): ArrayList<User> {
        val list = ArrayList<User>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUsersColumns.ID))
                val username =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUsersColumns.USERNAME))
                val avatarUrl =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUsersColumns.AVATAR_URL))
                list.add(
                    User(
                        username,
                        id,
                        avatarUrl
                    )
                )
            }

        } else {
            Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
        }

        return list
    }
}