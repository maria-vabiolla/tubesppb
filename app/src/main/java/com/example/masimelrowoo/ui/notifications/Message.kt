package com.example.masimelrowoo.ui.notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message (
    var date: String,
    var message: String
) : Parcelable