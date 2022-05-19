package com.example.masimelrowoo.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FriendListColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val FRIENDNAME = "friendName"
        }
    }
}