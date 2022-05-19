package com.example.masimelrowoo.helper

import android.database.Cursor
import com.example.masimelrowoo.db.DatabaseContract
import com.example.masimelrowoo.entity.FriendList

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FriendList> {
        val notesList = ArrayList<FriendList>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FriendListColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FriendListColumns.FRIENDNAME))
                notesList.add(FriendList(id, title))
            }
        }
        return notesList
    }
}