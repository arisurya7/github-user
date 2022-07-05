package com.arisurya.project.githubuser3.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.arisurya.project.githubuser3.data.local.DatabaseUser
import com.arisurya.project.githubuser3.data.local.FavoriteUserDao

class UserContentProvider : ContentProvider() {
    companion object{
        private const val AUTHORITY = "com.arisurya.project.githubuser3"
        private const val TABLE_NAME = "tb_favorite_user"
        const val ID_FAVORITE_USER_DATA = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORITE_USER_DATA)
        }
    }

    private lateinit var userDao : FavoriteUserDao
    override fun onCreate(): Boolean {
        DatabaseUser.getDatabase(context)
        userDao = DatabaseUser.getDatabase(context)?.favoriteUserDao() ?: return false
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        val cursor:Cursor?
        when(uriMatcher.match(uri)){
            ID_FAVORITE_USER_DATA ->{
                cursor = userDao.findAll()
                if(context!=null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }

        return cursor

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return  0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return  0
    }
}
