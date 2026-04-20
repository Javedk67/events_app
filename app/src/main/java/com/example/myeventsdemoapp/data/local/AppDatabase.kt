package com.example.myeventsdemoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myeventsdemoapp.data.local.dao.EventDao
import com.example.myeventsdemoapp.data.local.entity.BookmarkEntity
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.data.local.entity.EventEntity


@Database( entities = [
    EventEntity::class,
    BookmarkEntity::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}