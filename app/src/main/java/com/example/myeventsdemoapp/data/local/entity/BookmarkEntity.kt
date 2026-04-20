package com.example.myeventsdemoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val eventId: String
)
