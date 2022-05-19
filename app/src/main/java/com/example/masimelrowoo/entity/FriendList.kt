package com.example.masimelrowoo.entity

import android.os.Parcel
import android.os.Parcelable

data class FriendList (
    var id: Int = 0,
    var friendName: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(friendName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendList> {
        override fun createFromParcel(parcel: Parcel): FriendList {
            return FriendList(parcel)
        }

        override fun newArray(size: Int): Array<FriendList?> {
            return arrayOfNulls(size)
        }
    }
}