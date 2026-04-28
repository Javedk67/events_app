package com.example.myeventsdemoapp.data.local.entity


data class Event(
    val id: String,
    val title: String,
    val location: String,
    val lat: Double,
    val lng: Double,
    val time: Long,
    val imageUrl: String,
    val isBookmarked: Boolean,
    val distance: Float = 0f
)