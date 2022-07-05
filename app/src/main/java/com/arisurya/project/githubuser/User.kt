package com.arisurya.project.githubuser

import android.os.Parcel
import android.os.Parcelable


data class User(
    var name: String = "",
    var username: String = "",
    var location: String = "",
    var company: String = "",
    var follower: Int = 0,
    var following: Int = 0,
    var repository: Int = 0,
    var avatar: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(location)
        parcel.writeString(company)
        parcel.writeInt(follower)
        parcel.writeInt(following)
        parcel.writeInt(repository)
        parcel.writeInt(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}