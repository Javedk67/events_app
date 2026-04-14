package com.example.myeventsdemoapp.data.repository



import com.example.myeventsdemoapp.data.local.dao.EventDao
import com.example.myeventsdemoapp.data.local.entity.EventEntity
import com.example.myeventsdemoapp.data.remote.api.ApiService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val api: ApiService,
    private val dao: EventDao
) {

    suspend fun getEvents(): List<EventEntity> {

        val local = dao.getEvents()

        val isValid = local.isNotEmpty() &&
                System.currentTimeMillis() - local[0].lastUpdated < 10 * 60 * 1000

        return if (isValid) {
            local
        } else {
            val remote = api.getEvents().map {
                EventEntity(
                    id = it.id,
                    title = it.title,
                    location = it.location,
                    time = it.time,
                    imageUrl = it.imageUrl
                )
            }
            dao.insert(remote)
            remote
        }
    }

    suspend fun bookmark(id: String, state: Boolean) {
        dao.updateBookmark(id, state)
    }
}