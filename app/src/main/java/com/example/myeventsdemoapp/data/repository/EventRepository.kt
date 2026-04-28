package com.example.myeventsdemoapp.data.repository


import android.util.Log
import com.example.myeventsdemoapp.data.local.dao.EventDao

import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.data.local.entity.EventEntity
import com.example.myeventsdemoapp.data.remote.api.ApiService
import com.example.myeventsdemoapp.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EventRepository @Inject constructor(
    private val api: ApiService,
    private val dao: EventDao,

    ) {

    fun getEvents(): Flow<List<Event>> = flow {
        //  Cache check
        if (EventCache.isValid()) {
            emit(EventCache.get()!!)
            return@flow
        }
        val local = dao.getEvents()
        if (local.isNotEmpty()) {
            emit(local.map { it.toDomain() })
        }

        try {
            val remote = api.getEvents()

            val existingMap = local.associateBy { it.id }

            val updated = remote.body()!!.map { dto ->
                val existing = existingMap[dto.id]
                EventEntity(
                    id = dto.id,
                    title = dto.title,
                    location = dto.location,
                    lat = dto.lat,
                    lng = dto.lng,
                    time = dto.time,
                    imageUrl = dto.imageUrl,
                    isBookmarked = existing?.isBookmarked ?: false,
                    lastUpdated = System.currentTimeMillis()
                )
            }

            dao.insertAll(updated)
            emit(updated.map { it.toDomain() })


        } catch (e: Exception) {
            emit(local.map { it.toDomain() })
        }
    }

    suspend fun toggleBookmark(event: Event) {
        val newValue = event.isBookmarked
        dao.updateBookmark(event.id, newValue)
    }
}