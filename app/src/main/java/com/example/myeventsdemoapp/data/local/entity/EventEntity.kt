package com.example.myeventsdemoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val location: String,
    val time: String,
    val imageUrl: String,
    val isBookmarked: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)