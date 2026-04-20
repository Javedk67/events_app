package com.example.myeventsdemoapp.data.local.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val id: String,
    val title: String,
    val location: String,
    val lat: Double,
    val lng: Double,
    val time: Long,
    val imageUrl: String,
    val isBookmarked: Boolean = false,
    val distance: Float = 0f,
    val lastUpdated: Long = System.currentTimeMillis()
):Parcelable