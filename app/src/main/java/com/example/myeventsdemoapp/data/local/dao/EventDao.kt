package com.example.myeventsdemoapp.data.local.dao

import androidx.room.*
import com.example.myeventsdemoapp.data.local.entity.EventEntity


@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query("UPDATE events SET isBookmarked = :value WHERE id = :id")
    suspend fun updateBookmark(id: String, value: Boolean)

}