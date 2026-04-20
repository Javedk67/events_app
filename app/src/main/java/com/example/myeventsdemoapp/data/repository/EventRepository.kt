package com.example.myeventsdemoapp.data.repository



import android.util.Log
import com.example.myeventsdemoapp.data.local.dao.EventDao
import com.example.myeventsdemoapp.data.local.entity.BookmarkEntity
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.data.remote.api.ApiService
import com.example.myeventsdemoapp.utils.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EventRepository @Inject constructor(
    private val api: ApiService,
    private val dao: EventDao,

) {

    private var userLat: Double? = null
    private var userLng: Double? = null

    //    fun getLocation(lat: Double, lng: Double): Flow<List<Event>> = flow {
//
//        userLat=lat
//        userLng=lng
//    }
    fun getEvents(): Flow<List<Event>> = flow {

        // 1. Cache check
        if (EventCache.isValid()) {
            emit(EventCache.get()!!)
            return@flow
        }

        // 2. Emit local DB first (offline-first)
        val local = dao.getEvents()
        if (local.isNotEmpty()) {
            emit(local.map { it.toDomain() })
        }

        // 3. Fetch remote
        try {
            val response = api.getEvents()

            //emit(remote)
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                val events = body.map { it.toDomain() }
                Log.d("//////", "" + userLat)
//                val eventsWithDistance = events.withDistance(userLat!!, userLng!!)
//
//                EventCache.set(eventsWithDistance)
//
//                dao.insertAll(eventsWithDistance.map { it.toEntity() })
//
//                emit(eventsWithDistance)
                EventCache.set(events)
                dao.insertAll(events.map {
                    it.toEntity()
                })
                emit(events)
            } else {
                emit(dao.getEvents().map { it.toDomain() })

            }

        } catch (e: Exception) {
            emit(local.map { it.toDomain() })
        }
    }


    fun getBookmarkedIds(): Flow<List<String>> = flow {

        emit(dao.getBookMarksIds())

    }

    suspend fun toggleBookmark(event: Event, isBookmarked:Boolean) {

        if (isBookmarked) {
            dao.removeBookmark(event.id)

        } else {
            dao.addBookmark(BookmarkEntity(event.id))

        }
    }

}