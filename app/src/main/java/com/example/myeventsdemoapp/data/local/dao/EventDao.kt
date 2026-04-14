package com.example.myeventsdemoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myeventsdemoapp.data.local.entity.EventEntity


@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(events: List<EventEntity>)

    @Query("UPDATE events SET isBookmarked = :state WHERE id = :id")
    suspend fun updateBookmark(id: String, state: Boolean)
}