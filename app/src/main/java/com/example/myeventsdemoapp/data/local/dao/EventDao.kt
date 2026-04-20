package com.example.myeventsdemoapp.data.local.dao

import androidx.room.*
import com.example.myeventsdemoapp.data.local.entity.BookmarkEntity
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE eventId = :id)")
    suspend fun isBookmarked(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE eventId = :id")
    suspend fun removeBookmark(id: String)

    @Query("SELECT eventId FROM bookmarks")
    suspend fun getBookMarksIds():List<String>
   // fun getBookMarksIds(): Flow<List<String>>
}